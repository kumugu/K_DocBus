import { useState, useRef, useEffect } from 'react';
import { useCodeMirror } from '@uiw/react-codemirror';
import { markdown } from '@codemirror/lang-markdown';
import ReactMarkdown from 'react-markdown';

export default function MarkdownEditor() {
  const [markdownText, setMarkdownText] = useState('');
  const containerRef = useRef<HTMLDivElement>(null);

  const { setContainer, ...state } = useCodeMirror({
    value: markdownText,
    height: '200px',
    extensions: [markdown()],
    onChange: (value) => {
      setMarkdownText(value);
    },
  });

  // useEffect로 CodeMirror 연결
  useEffect(() => {
    if (containerRef.current) {
      setContainer(containerRef.current);
    }
  }, [setContainer]);

  return (
    <div className="flex flex-col items-center justify-center p-4">
      <h1 className="text-3xl font-bold mb-4">Markdown Editor</h1>

      {/* 마크다운 에디터 */}
      <div ref={containerRef} className="border border-gray-300 rounded mb-4" />

      {/* 마크다운 렌더링 */}
      <div className="w-full p-4 border border-gray-300 rounded">
        <ReactMarkdown>{markdownText}</ReactMarkdown>
      </div>
    </div>
  );
}
