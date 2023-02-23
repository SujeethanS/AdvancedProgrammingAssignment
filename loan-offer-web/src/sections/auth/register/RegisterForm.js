import { useState, useEffect } from 'react';
import { toast } from 'react-toastify'
import { useNavigate } from 'react-router-dom';
// @mui
import { Stack, TextField, MenuItem } from '@mui/material';
import { LoadingButton } from '@mui/lab';
import messageStyle  from '../../../utils/messageStyle'
// post api call
import { http } from '../../../service/APIService';
import BasicDatePicker from '../../../utils/BasicDatePicker';

// ----------------------------------------------------------------------

export default function RegisterForm() {

  const notification = (msg) => toast.error(msg, messageStyle);
  const navigate = useNavigate();

  const [myState, setMyState] = useState({
    firstName:"",
    lastName:"",
    email:"",
    nic:"",
    mobileNumber:"",
    dob:"",
    installPlan:"",
  });

  const [myArray, setMyArray] = useState([]);
  
  useEffect(() => {
    http('/get/installment/list').then(response=>{
      if(response.data.status === 3000){
        console.log("")
        setMyArray(response.data.value)
      }
    })
  },[]);

  const handleChange = (event) => {
    setMyState({
      ...myState,
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
      "userName": myState.email,
      "userEmail": myState.email,
      "userMobileNumber": myState.mobileNumber,
      "nic": myState.nic,
      "customerId": 0,
      "loanBalance": 15000,
      "usedAmount": 0,
      "installPlan": myState.installPlan
      }

      http('/create/new/user',registerRequest).then(response=>{
        if(response.data.status === 3000){
          navigate('/login', { replace: true });
        }else {
          notification(response.data.msg);
        }
      })
  };

  const datePickerOnChange = (value) => {
    myState.dob = value.format('YYYY-MM-DD')
  }

  return (
    <>
      <Stack spacing={3}>

      <Stack direction="row" spacing={3}>
        <TextField name="firstName" label="First Name" fullWidth value={myState.firstName} onChange={handleChange}/>
        <TextField name="lastName" label="Last Name" fullWidth value={myState.lastName} onChange={handleChange}/>
      </Stack>  

      <TextField name="email" label="Email address" value={myState.email} onChange={handleChange}/>
        
      <Stack direction="row" spacing={3}>
        <TextField name="nic" label="National ID" fullWidth value={myState.nic} onChange={handleChange}/>
        <TextField name="mobileNumber" fullWidth label="Mobile Number" value={myState.mobileNumber} onChange={handleChange}/>
      </Stack>

      <Stack direction="row" spacing={3}>
        <BasicDatePicker datePickerOnChange={datePickerOnChange} fullWidth />
        {/* <TextField name="dob" label="Date of Birth" fullWidth value={myState.dob} onChange={handleChange}/> */}
        <TextField name="installPlan" label="Install Plan" fullWidth value={myState.installPlan} onChange={handleChange} select>
          {myArray && myArray.length > 0 && 
            myArray.map(item => (
              <MenuItem key={item.installmentPlanId} value={item.installmentPlanId}>{item.planName}</MenuItem>
            ))
          }
        </TextField>
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
