import { axiosInstanceToken } from './config';

const postImageUpload = async (formData: any) => {
  return axiosInstanceToken({
    method: 'POST',
    url: `/api/image-upload`,
    headers: { 'Content-Type': 'multipart/form-data' },
    data: formData,
  });
};

const apiImage = { postImageUpload };

export default apiImage;
