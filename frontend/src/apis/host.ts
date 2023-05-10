import { AxiosResponse } from 'axios';

import { ID } from '@/types';
import { ApiEntranceCodedData, ApiHostProfileData } from '@/types/apis';

import { axiosInstance, axiosInstanceToken } from './config';

const getEntranceCode = async () => {
  const { data }: AxiosResponse<ApiEntranceCodedData> = await axiosInstanceToken({
    method: 'GET',
    url: `/api/hosts/entranceCode`,
  });

  return data;
};

const getHostProfile = async (hostId: ID) => {
  const { data }: AxiosResponse<ApiHostProfileData> = await axiosInstance({
    method: 'GET',
    url: `/api/hosts/${hostId}/profile`,
  });

  return data;
};

const putHostProfile = async (nickname: string) => {
  const { data }: AxiosResponse<ApiHostProfileData> = await axiosInstanceToken({
    method: 'PUT',
    url: `/api/hosts`,
    data: {
      nickname,
    },
  });

  return data;
};

const apiHost = { getEntranceCode, getHostProfile, putHostProfile };

export default apiHost;
