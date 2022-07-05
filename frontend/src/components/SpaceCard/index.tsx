/**  @jsxImportSource @emotion/react */
import CardTitle from '../_common/CardTitle';
import styles from './styles';

type SpaceCardProps = {
  spaceName: string;
  imageURL: string;
};

const SpaceCard = ({ spaceName, imageURL }: SpaceCardProps) => {
  return (
    <div css={styles.spaceCard({ imageURL })}>
      <CardTitle>{spaceName}</CardTitle>
    </div>
  );
};

export default SpaceCard;
