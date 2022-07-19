import { AxiosResponse } from 'axios';

import { ApiSpacesData, ApiSpaceData } from '@/types/apis';

import { axiosInstanceToken } from './config';

const getSpaces = async () => {
  const { data }: AxiosResponse<ApiSpacesData> = await axiosInstanceToken({
    method: 'GET',
    url: `api/spaces`,
  });

  return data;
};

// Sprint 2----------

// space 생성
// Location : /api/spaces/{spaceId}
// TODO: type이 multipart/form-data, name, image 생각 하고 다시 만들어
// const postNewSpace = ({}) => {
//   return axiosInstanceToken({
//     method: 'POST',
//     url: `/api/spaces`,
//     data: {
//       name,
//       image,
//     },
//   });
// };

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

const apiSpace = { getSpaces, deleteSpace, getSpace };

export default apiSpace;
