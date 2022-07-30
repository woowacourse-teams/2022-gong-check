import { useMemo } from 'react';
import { useMutation, useQuery } from 'react-query';
import { useNavigate } from 'react-router-dom';

import Button from '@/components/common/Button';
import SpaceDeleteModal from '@/components/host/SpaceDeleteModal';

import useModal from '@/hooks/useModal';
import useToast from '@/hooks/useToast';

import apiSpace from '@/apis/space';

import styles from './styles';

interface SpaceDeleteButtonProps {
  spaceId: string | undefined;
  spaceName: string | undefined;
}

const SpaceDeleteButton: React.FC<SpaceDeleteButtonProps> = ({ spaceId, spaceName }) => {
  const navigate = useNavigate();

  const { openModal, closeModal } = useModal();
  const { openToast } = useToast();

  const text = useMemo(() => `${spaceName} 공간을 삭제합니다`, [spaceName]);

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
      refetch();
      closeModal();
      openToast('SUCCESS', '공간이 삭제되었습니다.');
    },
  });

  const onClickDeleteSpace = () => {
    openModal(<SpaceDeleteModal text={text} onClick={() => deleteSpace(spaceId)} />);
  };

  return (
    <Button css={styles.spaceDeleteButton} onClick={onClickDeleteSpace}>
      공간 삭제
    </Button>
  );
};

export default SpaceDeleteButton;
