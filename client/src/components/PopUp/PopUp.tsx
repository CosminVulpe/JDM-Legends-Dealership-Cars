import React, {Dispatch, SetStateAction, useEffect} from "react";
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
    NumberDecrementStepper
} from "@chakra-ui/react";
import {ApiGetCar, ApiPostCar} from "../Service/api-requests/ApiRequests";
import {Car, HistoryBid} from "../Service/interfaces/Interfaces";
import {successfulNotification} from "../Service/toastify-notification/ToastifyNotification";
import {ToastContainer} from "react-toastify";
import {ca} from "date-fns/locale";

interface Props {
    id: number,

    setHistoryBid: Dispatch<SetStateAction<HistoryBid>>,

    historyBid: {
        bidValue: number,
        timeOfTheBid: Date,
    },

    setHistoryBidList: Dispatch<SetStateAction<HistoryBid[]>>,

    car:Car
}

const PopUp: React.FC<Props> = ({
                                    id
                                    , setHistoryBid
                                    , historyBid
                                    , setHistoryBidList
                                    ,car
                                }) => {
    const {isOpen, onOpen, onClose} = useDisclosure();
    const formatBidValue = (val: number): string => `$` + val;

    const parseValue = (val: string): number => {
        return parseInt(val.replace(/^\$/, ""));
    }

    const handleOnClick = (): void => {
        onClose();

        setHistoryBid(prevState => ({
            ...prevState,
            timeOfTheBid: new Date()
        }));

        ApiPostCar("bid/" + id, historyBid)
            .then(() => successfulNotification("Bid placed successfully"))
            .catch(err => console.error(err));

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
                        </Text>
                    </ModalBody>

                    <ModalFooter>
                        <Button colorScheme='teal'
                                mr={3}
                                onClick={handleOnClick}
                                isDisabled={historyBid.bidValue < car.initialPrice}
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