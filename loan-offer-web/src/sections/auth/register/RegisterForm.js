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

  const [error,setError] = useState({
    firstName:false,
    lastName:false,
    dob:false,
    email:false,
    mobileNumber:false,
    nic:false,
    installPlan:false
  });

  // const [validAge,setValidAge] = useState({
  //   dob:false
  // });

  const handleClick = () => {

    // const ageDifMs = Date.now() - new Date(myState.dob).getTime();
    // const ageDate = new Date(ageDifMs);
    // const myAge = Math.abs(ageDate.getUTCFullYear() - 1970);

    // console.log("myAge",myAge)

    // const errorAgeObj = {
    //   dob:false
    // }

    // if(myAge < 18) {
    //   errorAgeObj.dob = true
    // }else{
    //   errorAgeObj.dob = false
    // }

    // setValidAge(errorAgeObj)

    const errorObj = {
      firstName:false,
      lastName:false,
      dob:false,
      email:false,
      mobileNumber:false,
      nic:false,
      installPlan:false
    }

    if(myState.firstName){
      errorObj.firstName = false
    }else{
      errorObj.firstName = true
    }

    if(myState.lastName){
      errorObj.lastName = false
    }else{
      errorObj.lastName = true
    }

    if(myState.dob){
      errorObj.dob = false
    }else{
      errorObj.dob = true
    }

    if(myState.email){
      errorObj.email = false
    }else{
      errorObj.email = true
    }

    if(myState.mobileNumber){
      errorObj.mobileNumber = false
    }else{
      errorObj.mobileNumber = true
    }

    if(myState.nic){
      errorObj.nic = false
    }else{
      errorObj.nic = true
    }

    if(myState.installPlan){
      errorObj.installPlan = false
    }else{
      errorObj.installPlan = true
    }
    
    setError(errorObj)

    if(!errorObj.firstName && !errorObj.lastName && !errorObj.dob && !errorObj.email && 
      !errorObj.mobileNumber && !errorObj.nic && !errorObj.installPlan){
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
      }
  };

  const datePickerOnChange = (value) => {
    myState.dob = value.format('YYYY-MM-DD')
  }

  return (
    <>
      <Stack spacing={3}>

      <Stack direction="row" spacing={3}>

        <TextField 
          name="firstName" 
          label="First Name" 
          fullWidth value={myState.firstName} 
          onChange={handleChange}
          helperText={error.firstName ? "Invalid First Name" : ""}
          error={error.firstName}
        />

        <TextField 
          name="lastName" 
          label="Last Name" 
          fullWidth value={myState.lastName} 
          onChange={handleChange}
          helperText={error.lastName ? "Invalid Last Name" : ""}
          error={error.lastName}
        />

      </Stack>  

      <TextField 
        name="email" 
        label="Email address" 
        value={myState.email} 
        onChange={handleChange}
        helperText={error.email ? "Invalid Email" : ""}
        error={error.email}
      />
        
      <Stack direction="row" spacing={3}>
        <TextField 
          name="nic" 
          label="National ID" 
          fullWidth value={myState.nic} 
          onChange={handleChange}
          helperText={error.nic ? "Invalid NIC" : ""}
          error={error.nic}
        />

        <TextField 
          name="mobileNumber" 
          fullWidth label="Mobile Number" 
          value={myState.mobileNumber} 
          onChange={handleChange}
          helperText={error.mobileNumber ? "Invalid Mobile Number" : ""}
          error={error.mobileNumber}
        />

      </Stack>

      <Stack direction="row" spacing={3}>
        <BasicDatePicker 
          name="dob"
          datePickerOnChange={datePickerOnChange} 
          fullWidth 
          value={myState.dob}
          helperText={error.dob ? "Invalid DOB" : ""}
          error={error.dob}
        />
        {/* <TextField name="dob" label="Date of Birth" fullWidth value={myState.dob} onChange={handleChange}/> */}
        <TextField 
        name="installPlan" 
        label="Installment Plan" 
        fullWidth 
        value={myState.installPlan} 
        helperText={error.installPlan ? "Invalid Installment Plan" : ""}
        error={error.installPlan}
        onChange={handleChange} select>
          {myArray && myArray.length > 0 && 
            myArray.map(item => (
              <MenuItem key={item.installmentPlanId} value={item.installmentPlanId}>{item.planName}</MenuItem>
            ))
          }
        </TextField>
      </Stack>

      </Stack>

      <Stack direction="row" alignItems="center" justifyContent="space-between" sx={{ my: 5 }}>
        <LoadingButton name="register" fullWidth size="large" type="submit" variant="contained" onClick={handleClick}>
          Register
        </LoadingButton>
      </Stack>
    </>
  );
}
