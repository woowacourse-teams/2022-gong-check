import { AxiosError } from 'axios';
import { useState } from 'react';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';

import useToast from '@/hooks/useToast';

import apiSpace from '@/apis/space';

import { ID } from '@/types';

import errorMessage from '@/constants/errorMessage';

const useSpaceUpdateForm = () => {
  const navigate = useNavigate();

  const [isActiveSubmit, setIsActiveSubmit] = useState(true);

  const { openToast } = useToast();

  const onChangeSpaceName = (e: React.ChangeEvent<HTMLInputElement>) => {
    const input = e.target as HTMLInputElement;

    const isExistValue = input.value.length > 0;
    setIsActiveSubmit(isExistValue);
  };

  const { mutate: updateSpace } = useMutation(
    ({ spaceId, name, imageUrl }: { spaceId: ID; name: string; imageUrl: string }) =>
      apiSpace.putSpace(spaceId, name, imageUrl),
    {
      onSuccess: (_, { spaceId }) => {
        openToast('SUCCESS', '공간 정보가 수정 되었습니다.');
        navigate(`/host/manage/${spaceId}`);
      },
      onError: (err: AxiosError<{ errorCode: keyof typeof errorMessage }>) => {
        openToast('ERROR', errorMessage[`${err.response?.data.errorCode!}`]);
      },
    }
  );

  const onSubmitUpdateSpace = async (e: React.FormEvent<HTMLFormElement>, imageUrl: string, spaceId: ID) => {
    e.preventDefault();
    const form = e.target as HTMLFormElement;
    const name = form['nameInput'].value;

    updateSpace({ spaceId, name, imageUrl });
  };

  return { isActiveSubmit, onChangeSpaceName, onSubmitUpdateSpace };
};

export default useSpaceUpdateForm;
