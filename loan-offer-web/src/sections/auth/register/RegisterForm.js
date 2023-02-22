import { useState } from 'react';
import { toast } from 'react-toastify'
import { useNavigate } from 'react-router-dom';
// @mui
import { Link, Stack, IconButton, InputAdornment, TextField, Checkbox } from '@mui/material';
import { LoadingButton } from '@mui/lab';
import messageStyle  from '../../../utils/messageStyle'
// post api call
import { http } from '../../../service/APIService';
// components
import Iconify from '../../../components/iconify';

// ----------------------------------------------------------------------

export default function RegisterForm() {

  const notification = (msg) => toast.error(msg, messageStyle);
  const navigate = useNavigate();

  const [myState, setMyState] = useState({
    firstName:"",
    lastName:"",
    userName:"",
    email:"",
    nic:"",
    mobileNumber:"",
    dob:""
  });

  const handleChange = (event) => {
    setMyState({
      ...myState,
      [event.target.name]:event.target.value,
      [event.target.name]:event.target.value,
      [event.target.name]:event.target.value,
      [event.target.name]:event.target.value,
      [event.target.name]:event.target.value,
      [event.target.name]:event.target.value,
      [event.target.name]:event.target.value
    })
  };  

  const handleClick = () => {
    const registerRequest = {
      "userId": 0,
      "firstName": myState.firstName,
      "lastName": myState.lastName,
      "dob": myState.dob,
      "userType": 2,
      "userName": myState.userName,
      "userEmail": myState.email,
      "userMobileNumber": myState.mobileNumber,
      "nic": myState.nic,
      "customerId": 0,
      "loanBalance": 15000,
      "usedAmount": 0,
      "installPlan": 1
      }

      http('/create/new/user',registerRequest).then(response=>{
        if(response.data.status === 3000){
          navigate('/login', { replace: true });
        }else {
          notification(response.data.msg);
        }
      })
  };

  return (
    <>
      <Stack spacing={3}>

        <TextField name="firstName" label="First Name" value={myState.firstName} onChange={handleChange}/>
        <TextField name="lastName" label="Last Name" value={myState.lastName} onChange={handleChange}/>

        <Stack direction="row" spacing={5}>
          <TextField name="userName" label="User Name" value={myState.userName} onChange={handleChange}/>
          <TextField name="email" label="Email address" value={myState.email} onChange={handleChange}/>
        </Stack>

        <Stack direction="row" spacing={2}>
          <TextField name="nic" label="National ID" value={myState.nic} onChange={handleChange}/>
          <TextField name="mobileNumber" label="Mobile Number" value={myState.mobileNumber} onChange={handleChange}/>
          <TextField name="dob" label="Date of Birth" value={myState.dob} onChange={handleChange}/>
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
