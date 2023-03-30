import React, { useState, useEffect } from "react";
import Box from '@mui/material/Box';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import FormControl from '@mui/material/FormControl';
import FormControlLabel from '@mui/material/FormControlLabel';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import Select from '@mui/material/Select';
import Switch from '@mui/material/Switch';
// import isEmail from "validator/lib/isEmail";
import TextField from "@mui/material/TextField";
import Iconify from '../../iconify';

export default function AddProductDialog() {
    const [open, setOpen] = React.useState(false);
    const [fullWidth, setFullWidth] = React.useState(true);
    const [maxWidth, setMaxWidth] = React.useState('sm');
    const [state, setState] = useState({
        firstName: "",
        lastName: "",
        email: "",
        dateOfBirth: "",
        mobile: "",
        accountType: "1",
    })
    const [error, setError] = useState({
        firstName: false,
        lastName: false,
        email: false,
        dateOfBirth: false,
        mobile: false,
        accountType: false,
    })

    const handleChange = (event) => {
        const { name, value } = event.target;
        setState({
            ...state,
            [name]: value
        })
    }

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleMaxWidthChange = (event) => {
        setMaxWidth(
            // @ts-expect-error autofill of arbitrary value is not handled.
            event.target.value,
        );
    };

    const handleFullWidthChange = (event) => {
        setFullWidth(event.target.checked);
    };

    const handleSubmit = () => {
        let errorCheck = 0;

        const errorObj = {
            firstName: false,
            lastName: false,
            email: false,
            dateOfBirth: false,
            mobile: false,
            accountType: false,
        }

        if (!state.firstName || state.firstName.length > 50) {
            errorCheck = 1;
            errorObj.firstName = true
        }
        if (!state.lastName || state.lastName.length > 50) {
            errorCheck = 1;
            errorObj.lastName = true
        }
        // if (!isEmail(state.email)) {
        //     errorCheck = 1;
        //     errorObj.email = true
        // }
        if (!state.dateOfBirth) {
            errorCheck = 1;
            errorObj.dateOfBirth = true
        }
        if (!state.mobile || state.mobile.length < 6) {
            errorCheck = 1;
            errorObj.mobile = true
        }
        if (!state.accountType) {
            errorCheck = 1;
            errorObj.accountType = true
        }

        setError(errorObj)

        if (errorCheck === 0) {
            const postData = {
                firstName: state.firstName,
                lastName: state.lastName,
                email: state.email,
                dateOfBirth: state.dateOfBirth,
                mobile: state.mobile,
                accountType: state.accountType,
            }

            // need to change after api integration
            handleClose()
            console.log("post Data", postData);
        }

    };

    return (
        <>
            <Button variant="contained" startIcon={<Iconify icon="eva:plus-fill" />} onClick={handleClickOpen}>
                New Category
            </Button>
            <Dialog
                fullWidth={fullWidth}
                maxWidth={maxWidth}
                open={open}
                onClose={handleClose}
            >
                <DialogTitle>Optional sizes</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        You can set my maximum width and whether to adapt or not.
                    </DialogContentText>

                    <TextField
                        size="small"
                        error={error.firstName}
                        id="outlined-error-helper-text"
                        label="First Name"
                        name="firstName"
                        value={state.firstName}
                        helperText={error.firstName && !state.firstName ? "This field is required" : state.firstName.length > 50 ? "This field is is too length" : ""}
                        onChange={handleChange}
                        variant="outlined"
                        fullWidth
                        style={{ marginTop: 20 }}
                    />
                    <TextField
                        size="small"
                        error={error.lastName}
                        id="outlined-error-helper-text"
                        label="Last Name"
                        name="lastName"
                        value={state.lastName}
                        helperText={error.lastName && !state.lastName ? "This field is required" : state.lastName.length > 50 ? "This field is is too length" : ""}
                        onChange={handleChange}
                        variant="outlined"
                        fullWidth
                        style={{ marginTop: 20 }}
                    />
                    <TextField
                        size="small"
                        error={error.email}
                        id="outlined-error-helper-text"
                        label="Email"
                        name="email"
                        value={state.email}
                        helperText={error.email && "Invalid email"}
                        onChange={handleChange}
                        variant="outlined"
                        fullWidth
                        style={{ marginTop: 20 }}
                    />
                    <TextField
                        size="small"
                        error={error.mobile}
                        id="outlined-error-helper-text"
                        label="Mobile"
                        name="mobile"
                        type={"number"}
                        value={state.mobile}
                        helperText={error.mobile && "Invalid phone number"}
                        onChange={handleChange}
                        variant="outlined"
                        fullWidth
                        style={{ marginTop: 20 }}
                    />
                    <TextField
                        size="small"
                        error={error.dateOfBirth}
                        id="outlined-error-helper-text"
                        label="Date of Birth"
                        name="dateOfBirth"
                        type={"date"}
                        value={state.dateOfBirth}
                        helperText={error.dateOfBirth && "This field is required"}
                        onChange={handleChange}
                        variant="outlined"
                        style={{ marginTop: 20 }}
                        InputLabelProps={{ shrink: true }}
                    />
                    <TextField
                        size="small"
                        select
                        error={error.accountType}
                        id="outlined-error-helper-text"
                        label="Account Type"
                        name="accountType"
                        value={state.accountType}
                        helperText={error.accountType && "Account type is required"}
                        onChange={handleChange}
                        variant="outlined"
                        fullWidth
                        style={{ marginTop: 20 }}
                    >
                        <MenuItem value={"1"}>Student</MenuItem>
                        <MenuItem value={"2"}>Admin</MenuItem>
                    </TextField>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Close</Button>
                    <Button onClick={handleSubmit} variant="contained" color="primary">Save</Button>
                </DialogActions>
            </Dialog>
        </>
    );
}