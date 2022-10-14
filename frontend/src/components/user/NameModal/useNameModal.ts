import { useState } from 'react';

const useNameModal = (completeJobs: (name: string) => void) => {
  const [name, setName] = useState('');
  const [isDisabledButton, setIsDisabledButton] = useState(true);

  const onChangeInput = (e: React.ChangeEvent<HTMLInputElement>) => {
    const isTyped = !!e.target.value;
    setName(e.target.value);
    setIsDisabledButton(!isTyped);
  };

  const onClickButton = () => {
    completeJobs(name);
  };

  return { onChangeInput, onClickButton, isDisabledButton, name };
};

export default useNameModal;
