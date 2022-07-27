import axios from 'axios';

const API_URL = process.env.REACT_APP_API_URL;

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
