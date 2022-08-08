import { axiosInstanceToken } from './config';

const postImageUpload = async (formData: FormData) => {
  const { data } = await axiosInstanceToken({
    method: 'POST',
    url: `/api/imageUpload`,
    headers: { 'Content-Type': 'multipart/form-data' },
    data: formData,
  });

  return data;
};

const apiImage = { postImageUpload };

export default apiImage;
