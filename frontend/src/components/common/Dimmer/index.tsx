import useModal from '@/hooks/useModal';

import styles from './styles';

interface DimmerProps {
  children: React.ReactNode;
  isAbleClick?: boolean;
}

const Dimmer: React.FC<DimmerProps> = ({ children, isAbleClick = true }) => {
  const { closeModal } = useModal();

  const onClickDimmed = (e: React.MouseEvent<HTMLElement>) => {
    if (e.currentTarget === e.target && isAbleClick) closeModal();
  };

  return (
    <div css={styles.dimmer} onClick={onClickDimmed}>
      {children}
    </div>
  );
};

export default Dimmer;
