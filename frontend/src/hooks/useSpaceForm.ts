import useToast from './useToast';
import { convertURLtoFile } from '@/utils/convertURLtoFile';
import { AxiosError } from 'axios';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';

import apiImage from '@/apis/image';
import apiSpace from '@/apis/space';

import { ID } from '@/types';

import DUMMY_LOCAL_IMAGE_URL from '@/assets/homeCover.png';

const useSpaceForm = () => {
  const navigate = useNavigate();
  const { openToast } = useToast();

  const { mutate: newSpace } = useMutation(
    ({ name, imageUrl }: { name: string; imageUrl: string | null }) => apiSpace.postNewSpace(name, imageUrl),
    {
      onSuccess: res => {
        const locationSplitted = res.headers.location.split('/');
        const spaceId: ID = locationSplitted[locationSplitted.length - 1];

        openToast('SUCCESS', '공간이 생성 되었습니다.');
        navigate(`/host/manage/${spaceId}`);
      },
      onError: (err: AxiosError<{ message: string }>) => {
        openToast('ERROR', `${err.response?.data.message}`);
      },
    }
  );

  const { mutate: createImage } = useMutation(
    ({ formData, name }: { formData: any; name: string }) => apiImage.postImageUpload(formData),
    {
      onSuccess: (res, { name }) => {
        const {
          data: { imageUrl },
        } = res;

        newSpace({ name, imageUrl });
      },
      onError: (err: AxiosError<{ message: string }>) => {
        openToast('ERROR', `${err.response?.data.message}`);
      },
    }
  );

  const { mutate: updateSpace } = useMutation(
    ({ formData, spaceId }: { formData: any; spaceId: ID | undefined }) => apiSpace.putSpace(formData, spaceId),
    {
      onSuccess: (_, { spaceId }) => {
        openToast('SUCCESS', '공간 정보가 수정 되었습니다.');
        navigate(`/host/manage/${spaceId}`);
      },
      onError: (err: AxiosError<{ message: string }>) => {
        openToast('ERROR', `${err.response?.data.message}`);
      },
    }
  );

  const createSpace = (formData: any, name: string, isExistImage: boolean) => {
    if (isExistImage) {
      createImage({ formData, name });
      return;
    }

    newSpace({ name, imageUrl: null });
  };

  const onSubmitCreateSpace = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const form = e.target as HTMLFormElement;
    const formData = new FormData();

    const name = form['nameInput'].value;
    const files = form['imageInput'].files;
    const file = files[0];
    const isExistImage = files.length > 0;

    if (isExistImage) {
      formData.append('image', file);
      formData.append('filename', file.name);
    }

    createSpace(formData, name, isExistImage);
  };

  const onSubmitUpdateSpace = async (
    e: React.FormEvent<HTMLFormElement>,
    imageUrl: string | undefined,
    spaceId: ID | undefined
  ) => {
    e.preventDefault();
    const form = e.target as HTMLFormElement;
    const formData = new FormData();

    if (imageUrl) {
      // TODO: S3 도입되면 data.imageUrl로 이미지 file 객체를 생성해야 한다.
      // await convertURLtoFile(data?.imageUrl).then(file => {
      await convertURLtoFile(DUMMY_LOCAL_IMAGE_URL).then(file => {
        formData.append('image', file);
      });
    }

    const name = form['nameInput'].value;
    const files = form['imageInput'].files;
    const file = files[0];

    formData.append('request', new Blob([JSON.stringify({ name })], { type: 'application/json' }));
    if (files.length) formData.append('image', file);

    updateSpace({ formData, spaceId });
  };

  return { onSubmitCreateSpace, onSubmitUpdateSpace };
};

export default useSpaceForm;
