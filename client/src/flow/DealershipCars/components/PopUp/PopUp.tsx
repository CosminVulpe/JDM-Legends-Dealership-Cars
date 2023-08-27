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
    NumberDecrementStepper,
    FormLabel,
    Input,
    FormHelperText,
    FormControl,
    Checkbox
} from "@chakra-ui/react";
import {ApiGetCar, ApiGetTemporaryUser, ApiPostHistoryBid} from "../Service/api-requests/ApiRequests";
import {Car, HistoryBid, TemporaryUser} from "../Service/interfaces/Interfaces";
import {successfulNotification, warningNotification} from "../Service/toastify-notification/ToastifyNotification";
import {ToastContainer} from "react-toastify";
import {useFormik} from "formik";
import AlertNotification from "../AlertNotification/AlerNotification";
import {setTemporaryUserInfo} from "../Service/session-storage/SessionStorage";


interface Props {
    id: number,

    setHistoryBid: Dispatch<SetStateAction<HistoryBid>>,

    historyBid: {
        bidValue: BigInt,
        timeOfTheBid: Date,
    },

    setHistoryBidList: Dispatch<SetStateAction<HistoryBid[]>>,

    car: Car
}

const spacingInputStyle = {
    marginTop: "1rem"
};

const REGEX_VALIDATE_NAME = /^[a-zA-Z ]{2,30}$/;
const REGEX_VALID_EMAIL_ADDRESS = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

const PopUp: React.FC<Props> = (props) => {

    const {id, setHistoryBid, historyBid, setHistoryBidList, car} = props;

    const {isOpen, onOpen, onClose} = useDisclosure();
    const [checkedCheckBox, setCheckedCheckBox] = useState({
        "YesButton": false,
        "NoButton": false
    });
    const[tempUsers, setTempUsers] = useState<TemporaryUser[]>([]);

    const formik = useFormik({
        initialValues: {
            userName: "",
            firstName: "",
            lastName: "",
            emailAddress: "",
            timeOfTheCreation: new Date()
        },
        onSubmit: () => undefined,
    });

    useEffect(() => {
        ApiGetCar("bid-list/" + id)
            .then(res => setHistoryBidList(res.data))
            .catch(err => console.log(err))
    }, []);

    const formatBidValue = (val: BigInt): string => `$` + val;

    const parseValue = (val: string): BigInt => BigInt(val.replace(/^\$/, ""));

    const handleOnClick = (): void => {
        onClose();

        const temporaryUser: TemporaryUser = {
            fullName: formik.values.firstName.concat(" ").concat(formik.values.lastName),
            userName: formik.values.userName,
            emailAddress: formik.values.emailAddress,
            checkInformationStoredTemporarily: checkedCheckBox["YesButton"]
        };
        setTemporaryUserInfo(temporaryUser);

        ApiPostHistoryBid("bid/" + id, {
            historyBid: historyBid,
            temporaryUser: temporaryUser
        })
            .then( (response :any) => {
                if (response.status === 200 || response.status === 201) {
                    successfulNotification("Bid placed successfully");
                    ApiGetCar("bid-list/" + id)
                        .then(res => setHistoryBidList(res.data))
                        .catch(err => console.log(err))
                }
            })
            .catch(err => console.error(err));

        setCheckedCheckBox({YesButton: false, NoButton: false});
        setHistoryBid(prevState => ({
            ...prevState,
            bidValue: BigInt(0),
            timeOfTheBid: new Date()
        }));
    }

    const handleOnChange = (valueStr: string): void => {
        setHistoryBid(prevState => ({
            ...prevState,
            bidValue: parseValue(valueStr)
        }));
    }

    const handleOpenModel = () => {
        onOpen();
        ApiGetTemporaryUser()
            .then( (res: any) => setTempUsers(res.data))
            .catch(() => warningNotification("Something went wrong"))
    }

    const isSubmitButtonDisable = (): boolean => {
        if (historyBid.bidValue > BigInt(car.initialPrice)) {
            if (checkedCheckBox["YesButton"] || checkedCheckBox["NoButton"]) {
                return false;
            }
        }
        return true;
    }
    const isInputValid = (text: string): boolean => !(REGEX_VALIDATE_NAME.test(text));

    const doesUsernameAlreadyExist: boolean = tempUsers.map(item => item.userName).includes(formik.values.userName);

    return (
        <>
            <ToastContainer/>
            <Button onClick={handleOpenModel}
                    colorScheme="teal">Bid Now</Button>
            <form onSubmit={formik.handleSubmit}>
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
                                {historyBid.bidValue < BigInt(car.initialPrice) &&
                                    <AlertNotification
                                        alertType={"error"}
                                        textAlert={"The bid is lower than car's price"}
                                    />
                                }
                                <FormControl>
                                    <FormLabel style={spacingInputStyle}>First name</FormLabel>
                                    <Input placeholder='First name'
                                           name="firstName"
                                           type="text"
                                           onChange={formik.handleChange}
                                           onBlur={formik.handleBlur}
                                           defaultValue={formik.initialValues.firstName}/>
                                    {isInputValid(formik.values.firstName) && (formik.touched.firstName) &&
                                        <AlertNotification
                                            alertType={"error"}
                                            textAlert={"First Name not valid"}
                                        />
                                    }
                                    <FormLabel style={spacingInputStyle}>Last name</FormLabel>
                                    <Input placeholder='Last name'
                                           name="lastName"
                                           type="text"
                                           onChange={formik.handleChange}
                                           onBlur={formik.handleBlur}
                                           defaultValue={formik.initialValues.lastName}/>
                                    {isInputValid(formik.values.lastName) && (formik.touched.lastName) &&
                                        <AlertNotification
                                            alertType={"error"}
                                            textAlert={"Last Name not valid"}
                                        />
                                    }

                                    <FormLabel style={spacingInputStyle}>Username</FormLabel>
                                    <Input placeholder='Username'
                                           name="userName"
                                           type="text"
                                           onChange={formik.handleChange}
                                           onBlur={formik.handleBlur}
                                           defaultValue={formik.initialValues.userName}/>
                                    { (doesUsernameAlreadyExist && formik.touched.userName) &&
                                        <AlertNotification
                                            alertType={"error"}
                                            textAlert={"Username is already taken"}
                                        />
                                    }

                                    <FormLabel style={spacingInputStyle}>Email address</FormLabel>
                                    <Input type='email' placeholder={'Email Address'}
                                           name="emailAddress"
                                           onChange={formik.handleChange}
                                           onBlur={formik.handleBlur}
                                           defaultValue={formik.initialValues.emailAddress}/>
                                    <FormHelperText>We'll never share your email.</FormHelperText>
                                    {!REGEX_VALID_EMAIL_ADDRESS.test(formik.values.emailAddress) && (formik.touched.emailAddress) &&
                                        <AlertNotification
                                            alertType={"error"}
                                            textAlert={"Email Address not valid"}
                                        />
                                    }

                                    <Text fontSize='md' style={spacingInputStyle}>Do you want to store your info
                                        locally?</Text>
                                    <Checkbox
                                        isChecked={checkedCheckBox["YesButton"]}
                                        onChange={(e) => setCheckedCheckBox({
                                            NoButton: false,
                                            YesButton: e.target.checked
                                        })}
                                        isDisabled={checkedCheckBox["NoButton"]}
                                        style={spacingInputStyle}>Yes</Checkbox>
                                    <Checkbox
                                        isChecked={checkedCheckBox["NoButton"]}
                                        onChange={(e) => setCheckedCheckBox({
                                            NoButton: e.target.checked,
                                            YesButton: false
                                        })}
                                        isDisabled={checkedCheckBox["YesButton"]}
                                        style={spacingInputStyle}>No</Checkbox>

                                    {checkedCheckBox["YesButton"] &&
                                        <AlertNotification
                                            alertType={"warning"}
                                            textAlert={"Your info is stored temporarily"}
                                        />
                                    }

                                </FormControl>
                            </Text>
                        </ModalBody>

                        <ModalFooter>
                            <Button colorScheme='teal'
                                    mr={3}
                                    onClick={handleOnClick}
                                    isDisabled={isSubmitButtonDisable()}
                                    type={"submit"}
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
            </form>
        </>
    )
}

export default PopUp;