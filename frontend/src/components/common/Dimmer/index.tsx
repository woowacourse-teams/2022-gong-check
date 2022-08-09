import useModal from '@/hooks/useModal';

import styles from './styles';

interface DimmerProps {
  children: React.ReactNode;
  mode: 'full' | 'mobile';
  isAbleClick?: boolean;
}

const Dimmer: React.FC<DimmerProps> = ({ children, isAbleClick = true, mode = 'full' }) => {
  const { closeModal } = useModal();

  const onClickDimmed = (e: React.MouseEvent<HTMLElement>) => {
    if (e.currentTarget === e.target && isAbleClick) closeModal();
  };

  return (
    <div css={styles.dimmer(mode)} onClick={onClickDimmed}>
      {children}
    </div>
  );
};

export default Dimmer;
