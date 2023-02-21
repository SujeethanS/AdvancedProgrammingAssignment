import axios from "axios";

// API Axios Post Call.
export const http = (url,data, isTokenRequired) => {
    const BASE_URL = "http://localhost:8080";
    const _http = axios.create({
        baseURL: BASE_URL
    });
    if(isTokenRequired){
      const token = localStorage.getItem('jwt');
      const headers = {
        Authorization: `Bearer ${token}`
      };
      return _http.post(url, data, {headers});
    }
    return _http.post(url, data);
}
