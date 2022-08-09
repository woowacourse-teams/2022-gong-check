import { AxiosError } from 'axios';
import { useState } from 'react';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';

import useModal from '@/hooks/useModal';
import useToast from '@/hooks/useToast';

import apis from '@/apis';

import { ID } from '@/types';

const useNameModal = (jobId: ID, hostId: ID) => {
  const navigate = useNavigate();

  const { openToast } = useToast();
  const { closeModal } = useModal();

  const [name, setName] = useState('');
  const [isDisabledButton, setIsDisabledButton] = useState(true);

  const { mutate: postJobComplete } = useMutation(() => apis.postJobComplete(jobId, name), {
    onSuccess: () => {
      alert('체크리스트가 정상적으로 제출되었습니다.');
      navigate(`/enter/${hostId}/spaces`);
    },
    onError: (err: AxiosError<{ message: string }>) => {
      openToast('ERROR', `${err.response?.data.message}`);
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
