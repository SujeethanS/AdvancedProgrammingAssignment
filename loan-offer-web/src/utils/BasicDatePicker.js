import { useState } from 'react';
import TextField from '@mui/material/TextField';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';

export default function BasicDatePicker(props) {
  const date = new Date();
  const [value, setValue] = useState(date);
  const {datePickerOnChange} = props

  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <DatePicker
        fullWidth
        label="Date of Birth"
        value={value}
        inputFormat={"YYYY-MM-DD"}
        onChange={(newValue) => {
          setValue(newValue);
          datePickerOnChange(newValue)
        }}
        renderInput={(params) => <TextField {...params} />}
      />
    </LocalizationProvider>
  );
}