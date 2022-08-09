import { AxiosError } from 'axios';
import { useState } from 'react';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';

import useToast from '@/hooks/useToast';

import apiSpace from '@/apis/space';

import { ID } from '@/types';

const useSpaceCreateForm = () => {
  const navigate = useNavigate();
  const [isActiveSubmit, setIsActiveSubmit] = useState(false);

  const { openToast } = useToast();

  const onChangeSpaceName = (e: React.ChangeEvent<HTMLInputElement>) => {
    const input = e.target as HTMLInputElement;

    const isExistValue = input.value.length > 0;
    setIsActiveSubmit(isExistValue);
  };

  const { mutate: createSpace } = useMutation(
    ({ name, imageUrl }: { name: string; imageUrl: string }) => apiSpace.postNewSpace(name, imageUrl),
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

  const onSubmitCreateSpace = (e: React.FormEvent<HTMLFormElement>, imageUrl: string) => {
    e.preventDefault();
    const form = e.target as HTMLFormElement;
    const name = form['nameInput'].value;

    createSpace({ name, imageUrl });
  };

  return { isActiveSubmit, onSubmitCreateSpace, onChangeSpaceName };
};

export default useSpaceCreateForm;
