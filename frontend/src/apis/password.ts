import { AxiosResponse } from 'axios';

import { ID } from '@/types';
import { ApiTokenData } from '@/types/apis';

import { axiosInstance, axiosInstanceToken } from './config';

const postPassword = async (hostId: ID, password: string) => {
  const { data }: AxiosResponse<ApiTokenData> = await axiosInstance({
    method: 'POST',
    url: `api/hosts/${hostId}/enter`,
    data: {
      password,
    },
  });

  return data;
};

const patchSpacePassword = (password: string) => {
  return axiosInstanceToken({
    method: 'PATCH',
    url: `/api/spacePassword`,
    data: {
      password,
    },
  });
};

const apiPassword = { postPassword, patchSpacePassword };

export default apiPassword;
