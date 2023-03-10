import { useState } from 'react';
import { toast } from 'react-toastify'
import { useNavigate } from 'react-router-dom';
// @mui
import { Stack, IconButton, InputAdornment, TextField } from '@mui/material';
// import axios from 'axios';
import { LoadingButton } from '@mui/lab';
// components
import Iconify from '../../../components/iconify';
import messageStyle  from '../../../utils/messageStyle'
// post api call
import { http } from '../../../service/APIService';
// ----------------------------------------------------------------------

export default function LoginForm() {

  const navigate = useNavigate();
  const notification = (msg) => toast.error(msg, messageStyle);
  const [showPassword, setShowPassword] = useState(false);

  const [myState, setMyState] = useState({
    userName:"",
    password:""
  });

  const handleChange = (event) => {
    setMyState({
      ...myState,
      [event.target.name]:event.target.value
    })
  }; 

  const handleClick = () =>  {
    const loginRequest = {
      "username": myState.userName,
      "password": myState.password
    }

    http('/user/login',loginRequest).then(response=>{
      if(response.data.status === 3000){
        localStorage.setItem('userId',response.data.value.userId);
        localStorage.setItem('userType',response.data.value.userType);
        localStorage.setItem('fullName',response.data.value.fullName);
        localStorage.setItem('userEmail',response.data.value.userEmail);
        localStorage.setItem('usedAmount',response.data.value.usedAmount);
        localStorage.setItem('installmentPlan',response.data.value.installmentPlan);
        navigate('/dashboard', { replace: true });
      }else {
        notification(response.data.msg);
      }
    })
  };

  return (
    <>
      <Stack spacing={5}>
        <TextField name="userName" label="User Name" value={myState.userName} onChange={handleChange}/>

        <TextField
          value={myState.password} 
          onChange={handleChange}
          name="password"
          label="Password"
          type={showPassword ? 'text' : 'password'}
          InputProps={{
            endAdornment: (
              <InputAdornment position="end">
                <IconButton onClick={() => setShowPassword(!showPassword)} edge="end">
                  <Iconify icon={showPassword ? 'eva:eye-fill' : 'eva:eye-off-fill'} />
                </IconButton>
              </InputAdornment>
            ),
          }}
        />
      </Stack>

      {/* <Stack direction="row" alignItems="center" justifyContent="space-between" sx={{ my: 2 }}>
        <Checkbox name="remember" label="Remember me" />
        <Link variant="subtitle2" underline="hover">
          Forgot password?
        </Link>
      </Stack> */}

        <Stack  spacing={5} direction="column" alignItems="center" justifyContent="space-between" sx={{ my: 5 }}>
          <LoadingButton fullWidth size="large" type="submit" variant="contained" onClick={handleClick}>
            Login
          </LoadingButton>
        </Stack>
      {/* <NavLink to="/register"> */}
      {/* </NavLink> */}
    </>
  );
}
