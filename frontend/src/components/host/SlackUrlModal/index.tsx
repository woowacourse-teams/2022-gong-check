import { Suspense } from 'react';

import Dimmer from '@/components/common/Dimmer';
import LoadingOverlay from '@/components/common/LoadingOverlay';
import SlackUrlBox from '@/components/host/SlackUrlModal/SlackUrlBox';

import { JobType } from '@/types';

import slackIcon from '@/assets/slackIcon.svg';

import ModalPortal from '@/portals/ModalPortal';

import styles from './styles';

interface SlackUrlModalProps {
  jobs: JobType[];
}

const SlackUrlModal: React.FC<SlackUrlModalProps> = ({ jobs }) => {
  return (
    <ModalPortal>
      <Dimmer>
        <div css={styles.container}>
          <div>
            <img css={styles.icon} src={slackIcon} alt="" />
            <h1 css={styles.title}>Slack 알림</h1>
          </div>
          <span css={styles.detail}>Slack 채널의 URL을 입력하세요.</span>
          <div css={styles.contents}>
            <Suspense fallback={<LoadingOverlay />}>
              {jobs.length === 0 ? (
                <div css={styles.noJobsInfo}>
                  <span>생성한 업무가 없습니다.</span>
                </div>
              ) : (
                jobs.map((job, index) => <SlackUrlBox key={index} jobName={job.name} jobId={job.id} />)
              )}
            </Suspense>
          </div>
        </div>
      </Dimmer>
    </ModalPortal>
  );
};

export default SlackUrlModal;
