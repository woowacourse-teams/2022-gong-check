import { AxiosResponse } from 'axios';

import { ApiEntranceCodedData, ApiHostProfileData } from '@/types/apis';

import { axiosInstanceToken } from './config';

const getEntranceCode = async () => {
  const { data }: AxiosResponse<ApiEntranceCodedData> = await axiosInstanceToken({
    method: 'GET',
    url: `/api/hosts/entranceCode`,
  });

  return data;
};

const getHostProfile = async () => {
  const { data }: AxiosResponse<ApiHostProfileData> = await axiosInstanceToken({
    method: 'GET',
    url: `/api/hosts/me`,
  });

  return data;
};

const apiHost = { getEntranceCode, getHostProfile };

export default apiHost;
