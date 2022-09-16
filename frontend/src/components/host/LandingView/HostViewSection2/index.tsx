import HostPicture from '../HostPicture';
import { useRef, useState } from 'react';

import useOnContainerScroll from '@/hooks/useOnContainerScroll';

import { HostImageType, ScreenModeType } from '@/types';

import createSpace_280w from '@/assets/createSpace-280w.webp';
import createSpace_360w from '@/assets/createSpace-360w.webp';
import createSpace_540w from '@/assets/createSpace-540w.webp';
import createSpace_800w from '@/assets/createSpace-800w.webp';
import createSpace_fallback from '@/assets/createSpace-fallback.jpg';
import dashboard_280w from '@/assets/dashboard-280w.webp';
import dashboard_360w from '@/assets/dashboard-360w.webp';
import dashboard_540w from '@/assets/dashboard-540w.webp';
import dashboard_800w from '@/assets/dashboard-800w.webp';
import dashboard_fallback from '@/assets/dashboard-fallback.jpg';
import edit2_280w from '@/assets/edit2-280w.webp';
import edit2_360w from '@/assets/edit2-360w.webp';
import edit2_540w from '@/assets/edit2-540w.webp';
import edit2_800w from '@/assets/edit2-800w.webp';
import edit2_fallback from '@/assets/edit2-fallback.jpg';
import edit_280w from '@/assets/edit-280w.webp';
import edit_360w from '@/assets/edit-360w.webp';
import edit_540w from '@/assets/edit-540w.webp';
import edit_800w from '@/assets/edit-800w.webp';
import edit_fallback from '@/assets/edit-fallback.jpg';

import theme from '@/styles/theme';

import styles from './styles';

const createSpace: HostImageType = {
  '280w': createSpace_280w,
  '360w': createSpace_360w,
  '540w': createSpace_540w,
  '800w': createSpace_800w,
  fallback: createSpace_fallback,
};

const dashboard: HostImageType = {
  '280w': dashboard_280w,
  '360w': dashboard_360w,
  '540w': dashboard_540w,
  '800w': dashboard_800w,
  fallback: dashboard_fallback,
};

const edit2: HostImageType = {
  '280w': edit2_280w,
  '360w': edit2_360w,
  '540w': edit2_540w,
  '800w': edit2_800w,
  fallback: edit2_fallback,
};

const edit: HostImageType = {
  '280w': edit_280w,
  '360w': edit_360w,
  '540w': edit_540w,
  '800w': edit_800w,
  fallback: edit_fallback,
};

const HostViewSection2 = ({ screenMode }: { screenMode: ScreenModeType }) => {
  const [eventNumber, setEventNumber] = useState(0);
  const sectionRef = useRef<HTMLDivElement>(null);

  const { dimension, scrollInfo } = useOnContainerScroll(sectionRef, () => {
    const progress = Math.min(Math.max(0, scrollInfo.scrollY / dimension.windowHeight), 1) * 100;
    if (progress === 0) return;

    if (progress >= 50) {
      setEventNumber(1);
      return;
    }
    setEventNumber(0);
  });

  return (
    <section css={styles.layout} ref={sectionRef}>
      <div css={styles.content}>
        <h1 css={styles.title(screenMode)}>내 공간 관리</h1>
        {eventNumber >= 1 && (
          <div css={styles.gridWrapper}>
            <HostPicture image={createSpace} css={styles.picture(theme.colors.primary)} />
            <HostPicture image={dashboard} css={styles.picture(theme.colors.primary)} />
            <HostPicture image={edit} css={styles.picture(theme.colors.green)} />
            <HostPicture image={edit2} css={styles.picture(theme.colors.green)} />
          </div>
        )}
      </div>
    </section>
  );
};

export default HostViewSection2;
