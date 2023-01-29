import React, {Dispatch, SetStateAction, useEffect, useState} from "react";
import {
    Button,
    ModalBody,
    ModalCloseButton,
    ModalContent,
    ModalHeader,
    useDisclosure,
    ModalOverlay,
    ModalFooter,
    Text,
    Modal,
    NumberInput,
    NumberInputField,
    NumberInputStepper,
    NumberIncrementStepper,
    NumberDecrementStepper, FormLabel, Input, FormHelperText, FormControl, Checkbox
} from "@chakra-ui/react";
import {ApiGetCar, ApiPostHistoryBid, ApiPostTemporaryUser} from "../Service/api-requests/ApiRequests";
import {TemporaryUser, Car, HistoryBid} from "../Service/interfaces/Interfaces";
import {successfulNotification} from "../Service/toastify-notification/ToastifyNotification";
import {ToastContainer} from "react-toastify";
import {useFormik} from "formik";
import {saveInfoUserLocal} from "../Service/local-storage/LocalStorage";

interface Props {
    id: number,

    setHistoryBid: Dispatch<SetStateAction<HistoryBid>>,

    historyBid: {
        bidValue: number,
        timeOfTheBid: Date,
    },

    setHistoryBidList: Dispatch<SetStateAction<HistoryBid[]>>,

    car: Car
}

const spacingInputStyle = {
    marginTop: "1rem"
};

const PopUp: React.FC<Props> = ({
                                    id
                                    , setHistoryBid
                                    , historyBid
                                    , setHistoryBidList
                                    , car
                                }) => {
    const {isOpen, onOpen, onClose} = useDisclosure();
    const [checkedCheckBox, setCheckedCheckBox] = useState({
        "YesButton": false,
        "NoButton": false
    });

    const formik = useFormik<TemporaryUser>({
        initialValues: {
            userName: "",
            firstName: "",
            lastName: "",
            emailAddress: ""
        },
        onSubmit: () => undefined
    });

    const formatBidValue = (val: number): string => `$` + val;

    const parseValue = (val: string): number => {
        return parseInt(val.replace(/^\$/, ""));
    }

    const handleOnClick = (): void => {
        onClose();

        setHistoryBid(prevState => ({
            ...prevState,
            bidValue: 0,
            timeOfTheBid: new Date()
        }));
        setCheckedCheckBox({YesButton: false, NoButton: false});

        if (checkedCheckBox["YesButton"]) {
            saveInfoUserLocal("userName", formik.values.userName);
        }

        ApiPostHistoryBid("bid/" + id, historyBid)
            .then(() => successfulNotification("Bid placed successfully"))
            .catch(err => console.error(err));

        ApiPostTemporaryUser("", formik.values)
            .then(res => res)
            .catch((error) => console.log(error));

        setTimeout(() => {
            ApiGetCar("bid-list/" + id)
                .then(res => setHistoryBidList(res.data))
                .catch(err => console.log(err))
        }, 2500);
    }

    useEffect(() => {
        ApiGetCar("bid-list/" + id)
            .then(res => setHistoryBidList(res.data))
            .catch(err => console.log(err))
    }, []);

    const handleOnChange = (valueStr: string): void => {
        setHistoryBid(prevState => ({
            ...prevState,
            bidValue: parseValue(valueStr)
        }));
    }

    const isSubmitButtonDisable = () => {
        if (historyBid.bidValue > car.initialPrice) {
            if (checkedCheckBox["YesButton"] || checkedCheckBox["NoButton"]) {
                return false;
            }
        }
        return true;
    }

    return (
        <>
            <ToastContainer/>
            <Button onClick={onOpen}
                    colorScheme="teal">Bid Now</Button>
            <Modal blockScrollOnMount={false}
                   isOpen={isOpen}
                   onClose={onClose}
                   isCentered={true}>
                <ModalOverlay/>
                <ModalContent>
                    <ModalHeader>Bid Total Sum</ModalHeader>
                    <ModalCloseButton/>
                    <ModalBody>
                        <Text fontWeight='bold' mb='1rem'>
                            Choose amount 💸
                            <NumberInput
                                onChange={handleOnChange}
                                value={formatBidValue(historyBid.bidValue)}
                                min={car.initialPrice}
                                keepWithinRange={false}
                                clampValueOnBlur={false}
                            >
                                <NumberInputField/>
                                <NumberInputStepper>
                                    <NumberIncrementStepper/>
                                    <NumberDecrementStepper/>
                                </NumberInputStepper>
                            </NumberInput>
                            <FormControl>
                                <FormLabel style={spacingInputStyle}>First name</FormLabel>
                                <Input placeholder='First name'
                                       name="firstName"
                                       type="text"
                                       onChange={formik.handleChange}
                                       defaultValue={formik.initialValues.firstName}/>

                                <FormLabel style={spacingInputStyle}>Last name</FormLabel>
                                <Input placeholder='Last name'
                                       name="lastName"
                                       type="text"
                                       onChange={formik.handleChange}
                                       defaultValue={formik.initialValues.lastName}/>

                                <FormLabel style={spacingInputStyle}>Username</FormLabel>
                                <Input placeholder='Username'
                                       name="userName"
                                       type="text"
                                       onChange={formik.handleChange}
                                       defaultValue={formik.initialValues.userName}/>

                                <FormLabel style={spacingInputStyle}>Email address</FormLabel>
                                <Input type='email' placeholder={'Email Address'}
                                       name="emailAddress"
                                       onChange={formik.handleChange}
                                       defaultValue={formik.initialValues.emailAddress}/>
                                <FormHelperText>We'll never share your email.</FormHelperText>

                                <Text fontSize='md' style={spacingInputStyle}>Do you want to store your info
                                    locally?</Text>
                                <Checkbox
                                    isChecked={checkedCheckBox["YesButton"]}
                                    onChange={(e) => setCheckedCheckBox({NoButton: false, YesButton: e.target.checked})}
                                    isDisabled={checkedCheckBox["NoButton"]}
                                    style={spacingInputStyle}>Yes</Checkbox>
                                <Checkbox
                                    isChecked={checkedCheckBox["NoButton"]}
                                    onChange={(e) => setCheckedCheckBox({NoButton: e.target.checked, YesButton: false})}
                                    isDisabled={checkedCheckBox["YesButton"]}
                                    style={spacingInputStyle}>No</Checkbox>

                            </FormControl>
                        </Text>
                    </ModalBody>

                    <ModalFooter>
                        <Button colorScheme='teal'
                                mr={3}
                                onClick={handleOnClick}
                                isDisabled={isSubmitButtonDisable()}
                        >
                            Submit
                        </Button>
                        <Button variant='solid'
                                onClick={onClose}>
                            Close
                        </Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    )
}

export default PopUp;