import { AxiosResponse } from 'axios';

import { ApiTokenData } from '@/types/apis';

import { axiosInstance, axiosInstanceToken } from './config';

const postPassword = async ({ hostId, password }: any) => {
  const { data }: AxiosResponse<ApiTokenData> = await axiosInstance({
    method: 'POST',
    url: `api/hosts/${hostId}/enter`,
    data: {
      password,
    },
  });

  return data;
};

// /api/spacePassword
// space password 수정
const patchSpacePassword = (password: number | string) => {
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
