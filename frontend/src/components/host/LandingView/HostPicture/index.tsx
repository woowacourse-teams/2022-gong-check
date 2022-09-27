import { HostImageType } from 'gongcheck-util-types/image';

import screenSize from '@/constants/screenSize';

interface HostPictureProps {
  image: HostImageType;
  className?: string;
}

const HostPicture: React.FC<HostPictureProps> = ({ image, className }) => {
  return (
    <picture className={className}>
      <source media={`(max-width: ${screenSize.DESKTOP}px)`} type="image/webp" srcSet={image['280w']} />
      <source media={`(max-width: ${screenSize.DESKTOP_BIC}px)`} type="image/webp" srcSet={image['360w']} />
      <source type="image/webp" srcSet={image['540w']} />
      <img src={image.fallback} alt="" />
    </picture>
  );
};

export default HostPicture;
