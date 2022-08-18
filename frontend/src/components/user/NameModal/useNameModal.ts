import { AxiosError } from 'axios';
import { useState } from 'react';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';

import useModal from '@/hooks/useModal';
import useToast from '@/hooks/useToast';

import apis from '@/apis';

import { ID } from '@/types';

import errorMessage from '@/constants/errorMessage';

const useNameModal = (jobId: ID) => {
  const { openToast } = useToast();
  const { closeModal } = useModal();

  const [name, setName] = useState('');
  const [isDisabledButton, setIsDisabledButton] = useState(true);

  const { mutate: postJobComplete } = useMutation(() => apis.postJobComplete(jobId, name), {
    onError: (err: AxiosError<{ errorCode: keyof typeof errorMessage }>) => {
      openToast('ERROR', errorMessage[`${err.response?.data.errorCode!}`]);
      closeModal();
    },
  });

  const onChangeInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    const isTyped = !!e.target.value;
    setName(e.target.value);
    setIsDisabledButton(!isTyped);
  };

  const onClickButton = () => {
    postJobComplete();
  };

  return { onChangeInput, onClickButton, isDisabledButton, name };
};

export default useNameModal;
