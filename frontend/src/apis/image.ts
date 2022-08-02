import { axiosInstanceToken } from './config';

const postImageUpload = async (formData: any) => {
  const { data } = await axiosInstanceToken({
    method: 'POST',
    url: `/api/image-upload`,
    headers: { 'Content-Type': 'multipart/form-data' },
    data: formData,
  });

  return data;
};

const apiImage = { postImageUpload };

export default apiImage;
