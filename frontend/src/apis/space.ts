import { AxiosResponse } from 'axios';

import { ApiSpacesData, ApiSpaceData } from '@/types/apis';

import { axiosInstanceToken } from './config';

const getSpaces = async () => {
  const { data }: AxiosResponse<ApiSpacesData> = await axiosInstanceToken({
    method: 'GET',
    url: `/api/spaces`,
  });

  return data;
};

// space 생성
const postNewSpace = ({ formData }: any) => {
  return axiosInstanceToken({
    method: 'POST',
    url: `/api/spaces`,
    headers: { 'Content-Type': 'multipart/form-data' },
    data: formData,
  });
};

// space 삭제
const deleteSpace = ({ spaceId }: any) => {
  return axiosInstanceToken({
    method: 'DELETE',
    url: `/api/spaces/${spaceId}`,
  });
};

// space 단건 조회
const getSpace = async ({ spaceId }: any) => {
  const { data }: AxiosResponse<ApiSpaceData> = await axiosInstanceToken({
    method: 'GET',
    url: `/api/spaces/${spaceId}`,
  });

  return data;
};

const apiSpace = { getSpaces, postNewSpace, deleteSpace, getSpace };

export default apiSpace;
