import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import useModal from '@/hooks/useModal';

import apis from '@/apis';

const useNameModal = (jobId: string | undefined, hostId: string | undefined) => {
  const navigate = useNavigate();

  const [name, setName] = useState('');
  const [isDisabledButton, setIsDisabledButton] = useState(true);

  const { closeModal } = useModal();

  const onChangeInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    const isTyped = !!e.target.value;
    setName(e.target.value);
    setIsDisabledButton(!isTyped);
  };

  const onClickButton = async () => {
    await apis.postJobComplete({ jobId, author: name });
    alert('제출 되었습니다.');
    closeModal();
    navigate(`/enter/${hostId}/spaces`);
  };

  return { onChangeInput, onClickButton, isDisabledButton, name };
};

export default useNameModal;
