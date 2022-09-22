import useHeroSection from './useHeroSection';
import { IoIosArrowDown } from '@react-icons/all-files/io/IoIosArrowDown';

import homeCover_360w from '@/assets/homeCover-360w.webp';
import homeCover_480w from '@/assets/homeCover-480w.webp';
import homeCover_fallback from '@/assets/homeCover-fallback.png';

import styles from './styles';

const HeroSection: React.FC = () => {
  const { isFull, onMouseMove, canvasRef } = useHeroSection();

  return (
    <>
      <section css={styles.layout(isFull)} onMouseMove={onMouseMove}>
        <div css={styles.content}>
          <div css={styles.homeCoverWrapper}>
            <picture css={styles.homeCover}>
              <source media={`(max-width: 360px`} type="image/webp" srcSet={homeCover_360w} />
              <source type="image/webp" srcSet={homeCover_480w} />
              <img src={homeCover_fallback} alt="" />
            </picture>
          </div>
          <div css={styles.arrowDownWrapper}>
            <span>아래로 내려주세요.</span>
            <IoIosArrowDown />
          </div>
          <canvas css={styles.canvas} ref={canvasRef}></canvas>
        </div>
      </section>
      <div css={styles.bottomWrapper(isFull)} />
    </>
  );
};

export default HeroSection;
