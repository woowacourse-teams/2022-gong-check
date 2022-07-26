import { useMutation, useQuery } from 'react-query';
import { useNavigate } from 'react-router-dom';

import Button from '@/components/common/Button';

import useToast from '@/hooks/useToast';

import apiSpace from '@/apis/space';

import styles from './styles';

interface SpaceDeleteButtonProps {
  spaceId: string | undefined;
}

const SpaceDeleteButton: React.FC<SpaceDeleteButtonProps> = ({ spaceId }) => {
  const navigate = useNavigate();

  const { openToast } = useToast();

  const { refetch } = useQuery(['deleteSpaces'], apiSpace.getSpaces, {
    suspense: true,
    enabled: false,
    onSuccess: data => {
      const { spaces } = data;

      if (spaces.length === 0) {
        navigate('/host/manage/spaceCreate');
        return;
      }

      const space = spaces[0];
      navigate(`/host/manage/${space.id}`);
    },
  });

  const { mutate: deleteSpace } = useMutation((spaceId: string | undefined) => apiSpace.deleteSpace(spaceId), {
    onSuccess: () => {
      openToast('SUCCESS', '공간이 삭제되었습니다.');
      refetch();
    },
  });

  const onClickDeleteSpace = () => {
    deleteSpace(spaceId);
  };

  return (
    <Button css={styles.spaceDeleteButton} onClick={onClickDeleteSpace}>
      공간 삭제
    </Button>
  );
};

export default SpaceDeleteButton;
