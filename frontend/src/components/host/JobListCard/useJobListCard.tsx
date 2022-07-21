import { useNavigate } from 'react-router-dom';

const useJobListCard = () => {
  const navigate = useNavigate();

  const onClickNewJobButton = () => {
    navigate('jobCreate');
  };

  return { onClickNewJobButton };
};

export default useJobListCard;
