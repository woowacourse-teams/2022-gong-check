import { AxiosResponse } from 'axios';

import { ID } from '@/types';
import { ApiSpacesData, ApiSpaceData } from '@/types/apis';

import { axiosInstanceToken } from './config';

const getSpaces = async () => {
  const { data }: AxiosResponse<ApiSpacesData> = await axiosInstanceToken({
    method: 'GET',
    url: `/api/spaces`,
  });

  return data;
};

const postNewSpace = (name: string, imageUrl: string) => {
  return axiosInstanceToken({
    method: 'POST',
    url: `/api/spaces`,
    data: { name, imageUrl },
  });
};

const deleteSpace = (spaceId: ID) => {
  return axiosInstanceToken({
    method: 'DELETE',
    url: `/api/spaces/${spaceId}`,
  });
};

const getSpace = async (spaceId: ID) => {
  const { data }: AxiosResponse<ApiSpaceData> = await axiosInstanceToken({
    method: 'GET',
    url: `/api/spaces/${spaceId}`,
  });

  return data;
};

const putSpace = (spaceId: ID, name: string, imageUrl: string) => {
  return axiosInstanceToken({
    method: 'PUT',
    url: `/api/spaces/${spaceId}`,
    data: { name, imageUrl },
  });
};

const apiSpace = { getSpaces, postNewSpace, deleteSpace, getSpace, putSpace };

export default apiSpace;
