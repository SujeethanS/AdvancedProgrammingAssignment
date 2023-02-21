import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
// @mui
import { Link, Stack, IconButton, InputAdornment, TextField, Checkbox } from '@mui/material';
import { LoadingButton } from '@mui/lab';
// components
import Iconify from '../../../components/iconify';

// ----------------------------------------------------------------------

export default function RegisterForm() {
  const navigate = useNavigate();

  const [showPassword, setShowPassword] = useState(false);

  const handleClick = () => {
    navigate('/dashboard', { replace: true });
  };

  return (
    <>
      <Stack spacing={3}>

        <TextField name="firstName" label="First Name" value={'Thevarasa'}/>
        <TextField name="lastName" label="Last Name" value={'Sujeethan'}/>

        <Stack direction="row" spacing={5}>
          <TextField name="userName" label="User Name" value={'Sujeethan'}/>
          <TextField name="email" label="Email address" value={'sujeethan@smartzi.com'}/>
        </Stack>

        <Stack direction="row" spacing={2}>
          <TextField name="nic" label="National ID" value={'940931258V'}/>
          <TextField name="mobileNumber" label="Mobile Number" value={'0775272030'}/>
          <TextField name="dob" label="Date of Birth" value={'1994-04-02'}/>
        </Stack>

      </Stack>

      <Stack direction="row" alignItems="center" justifyContent="space-between" sx={{ my: 5 }}>
        <LoadingButton fullWidth size="large" type="submit" variant="contained" onClick={handleClick}>
          Register
        </LoadingButton>
      </Stack>
    </>
  );
}
