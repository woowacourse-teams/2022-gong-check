import axios from 'axios';

const DEV_URL = 'http://localhost:8080';
// const PROD_URL = process.env.REACT_APP_API_ROOT;
// const BASE_URL = '';
const API_URL = DEV_URL;

const axiosInstance = axios.create({
  baseURL: API_URL,
});

const axiosInstanceToken = axios.create({
  baseURL: API_URL,
});

axiosInstanceToken.interceptors.request.use(
  config => {
    const accessToken = localStorage.getItem('user');

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

// 비밀번호 입력
const postPassword = async ({ hostId, password }: any) => {
  return await axiosInstance({
    method: 'POST',
    url: `api/hosts/${hostId}/enter`,
    data: {
      password,
    },
  });
};

// 공간 목록 {page,size}
const getSpaces = async () => {
  return await axiosInstanceToken({
    method: 'GET',
    url: `api/spaces`,
  });
};

// 업무 종류
const getJobs = async ({ spaceId }: any) => {
  return await axiosInstanceToken({
    method: 'GET',
    url: `/api/spaces/${spaceId}/jobs`,
  });
};

// 진행중인 작업이 있는지 확인
const getJobActive = async ({ jobId }: any) => {
  return await axiosInstanceToken({
    method: 'GET',
    url: `/api/jobs/${jobId}/active`,
  });
};

// 새 작업 생성
const postNewTasks = async ({ jobId }: any) => {
  return await axiosInstanceToken({
    method: 'POST',
    url: `/api/jobs/${jobId}/tasks/new`,
  });
};

// 진행중인 작업 불러오기
const getTasks = async ({ jobId }: any) => {
  return await axiosInstanceToken({
    method: 'GET',
    url: `/api/jobs/${jobId}/tasks`,
  });
};

// 체크박스 체크하기
const postCheckTask = async ({ taskId }: any) => {
  return await axiosInstanceToken({
    method: 'POST',
    url: `/api/tasks/${taskId}/flip`,
  });
};

// 제출하기
const postJobComplete = async ({ jobId, author }: any) => {
  return await axiosInstanceToken({
    method: 'POST',
    url: `/api/jobs/${jobId}/complete`,
    data: {
      author,
    },
  });
};

const apis = { postPassword, getSpaces, getJobs, getJobActive, postNewTasks, getTasks, postCheckTask, postJobComplete };

export default apis;
