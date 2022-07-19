import { axiosInstance, axiosInstanceToken } from '../config';
import { AxiosResponse } from 'axios';

import { ApiTokenData } from '@/types/apis';

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

// Sprint 2------

// /api/spacePassword
// space password 수정
const patchSpacePwassword = ({ password }: any) => {
  return axiosInstanceToken({
    method: 'PATCH',
    url: `/api/spacePassword`,
    data: {
      password,
    },
  });
};

const apis = { postPassword, patchSpacePwassword };

export default apis;
