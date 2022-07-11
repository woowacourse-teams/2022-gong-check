import { axiosInstance, axiosInstanceToken } from './config';
import { ApiSpacesData, ApiJobData, ApiTaskData, ApiJobActiveData } from '@/types/apis';
import { AxiosResponse } from 'axios';

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
  const { data }: AxiosResponse<ApiSpacesData> = await axiosInstanceToken({
    method: 'GET',
    url: `api/spaces`,
  });

  return data;
};

// 업무 종류
const getJobs = async ({ spaceId }: any) => {
  const { data }: AxiosResponse<ApiJobData> = await axiosInstanceToken({
    method: 'GET',
    url: `/api/spaces/${spaceId}/jobs`,
  });

  return data;
};

// 진행중인 작업이 있는지 확인
const getJobActive = async ({ jobId }: any) => {
  const { data }: AxiosResponse<ApiJobActiveData> = await axiosInstanceToken({
    method: 'GET',
    url: `/api/jobs/${jobId}/active`,
  });

  return data;
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
  const { data }: AxiosResponse<ApiTaskData> = await axiosInstanceToken({
    method: 'GET',
    url: `/api/jobs/${jobId}/tasks`,
  });

  return data;
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
