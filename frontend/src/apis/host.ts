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

const apiHost = { getEntranceCode, getHostProfile };

export default apiHost;
