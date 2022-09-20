import useHeroSection from './useHeroSection';
import { IoIosArrowDown } from '@react-icons/all-files/io/IoIosArrowDown';

import homeCover from '@/assets/homeCover.png';
import homeCoverWebp from '@/assets/homeCover.webp';

import styles from './styles';

const HeroSection: React.FC = () => {
  const { isFull, onMouseMove, canvasRef } = useHeroSection();

  return (
    <>
      <section css={styles.layout(isFull)} onMouseMove={onMouseMove}>
        <div css={styles.content}>
          <div css={styles.homeCoverWrapper}>
            <div id="커버" css={styles.homeCover}>
              <img css={styles.img} srcSet={`${homeCoverWebp}`} src={homeCover} alt="" />
            </div>
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
