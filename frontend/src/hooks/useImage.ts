import checkFileSize from '@/utils/checkFileSize';
import { AxiosError } from 'axios';
import { useEffect, useState } from 'react';
import { useMutation } from 'react-query';

import useToast from '@/hooks/useToast';

import apiImage from '@/apis/image';

const useImage = (prevImageUrl?: string) => {
  const { openToast } = useToast();

  const [imageUrl, setImageUrl] = useState('');

  const { mutateAsync: uploadImage } = useMutation((formData: FormData) => apiImage.postImageUpload(formData), {
    onError: (err: AxiosError<{ message: string }>) => {
      openToast('ERROR', `${err.response?.data.message}`);
    },
  });

  const onChangeImage = async (e: React.FormEvent<HTMLInputElement>) => {
    const input = e.target as HTMLInputElement;

    if (!input.files?.length) {
      return;
    }

    const file = input.files[0];
    const formData = new FormData();
    formData.append('image', file);

    const isOkayFileSize = checkFileSize(file);

    if (!isOkayFileSize) {
      input.value = '';
      return;
    }

    const { imageUrl: newImageUrl } = await uploadImage(formData);

    setImageUrl(newImageUrl);
  };

  useEffect(() => {
    if (!prevImageUrl) return;

    setImageUrl(prevImageUrl);
  }, [prevImageUrl]);

  return { imageUrl, onChangeImage };
};

export default useImage;