import axios from 'axios';

const DEV_URL = 'http://localhost:8080';
const PROD_URL = 'http://15.164.216.196:8080';
const API_URL = DEV_URL;

export const axiosInstance = axios.create({
  baseURL: API_URL,
});

export const axiosInstanceToken = axios.create({
  baseURL: API_URL,
});

axiosInstanceToken.interceptors.request.use(
  config => {
    const accessToken = localStorage.getItem('token');

    if (accessToken) {
      config.headers = {
        Authorization: `Bearer ${accessToken}`,
      };
    }

    return config;
  },
  error => {
    return Promise.reject(error);
  }
);
