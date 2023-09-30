import React from "react";
import {Alert, AlertIcon, Stack} from "@chakra-ui/react";

interface Props {
    textAlert: string,
    alertType: "info" | "warning" | "success" | "error" | "loading" | undefined;
}

const AlertNotification: React.FC<Props> = ({textAlert, alertType}) => {
    return (
        <Stack spacing={3} mt={2}>
            <Alert status={alertType} height='20px'>
                <AlertIcon/>
                <div>{textAlert}</div>
            </Alert>
        </Stack>
    )
}
export default AlertNotification