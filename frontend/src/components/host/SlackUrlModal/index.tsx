import { Suspense } from 'react';

import Dimmer from '@/components/common/Dimmer';
import SlackUrlBox from '@/components/host/SlackUrlBox';

import { JobType } from '@/types';

import slackIcon from '@/assets/slackIcon.svg';

import ModalPortal from '@/ModalPortal';

import styles from './styles';

interface SlackUrlModalProps {
  jobs: JobType[];
}

const SlackUrlModal: React.FC<SlackUrlModalProps> = ({ jobs }) => {
  return (
    <ModalPortal>
      <Dimmer mode="full">
        <div css={styles.container}>
          <div>
            <img css={styles.icon} src={slackIcon} alt="" />
            <h1 css={styles.title}>Slack 알림 URL</h1>
          </div>
          <span css={styles.detail}>사용하시는 Slack 채널의 URL을 수정하세요.</span>
          <div css={styles.contents}>
            <Suspense fallback={<div>슬랙 URL API 로딩 중...</div>}>
              {jobs.length === 0 ? (
                <div>생성해주세요.</div>
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
