import useToast from './useToast';
import { AxiosError } from 'axios';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';

import apiImage from '@/apis/image';
import apiSpace from '@/apis/space';

import { ID } from '@/types';

const useSpaceForm = () => {
  const navigate = useNavigate();
  const { openToast } = useToast();

  const { mutate: newSpace } = useMutation(
    ({ name, imageUrl }: { name: string; imageUrl: string | undefined }) => apiSpace.postNewSpace(name, imageUrl),
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

  const { mutateAsync: uploadImage } = useMutation((formData: any) => apiImage.postImageUpload(formData), {
    onError: (err: AxiosError<{ message: string }>) => {
      openToast('ERROR', `${err.response?.data.message}`);
    },
  });

  const { mutate: updatePutSpace } = useMutation(
    ({ spaceId, name, imageUrl }: { spaceId: ID | undefined; name: string; imageUrl: string | undefined }) =>
      apiSpace.putSpace(spaceId, name, imageUrl),
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

  const createSpace = async (formData: any, name: string, isExistImage: boolean) => {
    if (isExistImage) {
      const { imageUrl } = await uploadImage(formData);
      newSpace({ name, imageUrl });

      return;
    }

    newSpace({ name, imageUrl: undefined });
  };

  const updateSpace = async (
    formData: any,
    name: string,
    spaceId: ID | undefined,
    isExistImage: boolean,
    imageUrl: string | undefined
  ) => {
    if (isExistImage) {
      const { imageUrl } = await uploadImage(formData);
      updatePutSpace({ spaceId, name, imageUrl });

      return;
    }

    updatePutSpace({ spaceId, name, imageUrl });
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

    const name = form['nameInput'].value;
    const files = form['imageInput'].files;
    const file = files[0];
    const isExistImage = files.length > 0;

    if (isExistImage) {
      formData.append('image', file);
      formData.append('filename', file.name);
    }

    updateSpace(formData, name, spaceId, isExistImage, imageUrl);
  };

  return { onSubmitCreateSpace, onSubmitUpdateSpace };
};

export default useSpaceForm;
