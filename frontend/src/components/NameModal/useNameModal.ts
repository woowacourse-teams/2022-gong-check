import { useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import useModal from '@/hooks/useModal';

import apis from '@/apis';

const useNameModal = (jobId: string | undefined) => {
  const navigate = useNavigate();

  const [name, setName] = useState('');
  const [isDisabledButton, setIsDisabledButton] = useState(true);
  const { hostId } = useParams();

  const { closeModal } = useModal();

  const onChaneInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    const isTyped = !!e.target.value;
    setName(e.target.value);
    setIsDisabledButton(!isTyped);
  };

  const onClickButton = async () => {
    try {
      await apis.postJobComplete({ jobId, author: name });
      alert('제출 되었습니다.');
      closeModal();
      navigate(`/${hostId}/spaces`);
    } catch (err) {
      alert(err);
    }
  };

  return { onChaneInput, onClickButton, isDisabledButton, name };
};

export default useNameModal;
