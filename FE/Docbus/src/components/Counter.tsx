import { useCounterStore } from '../store/useCounterStore';

export default function Counter() {
  const { count, increase, decrease } = useCounterStore();

  return (
    <div className="flex flex-col items-center justify-center p-4">
      <h1 className="text-2xl font-bold">Counter: {count}</h1>
      <div className="flex gap-4 mt-4">
        <button className="px-4 py-2 bg-blue-500 text-white rounded" onClick={increase}>
          +1 증가
        </button>
        <button className="px-4 py-2 bg-red-500 text-white rounded" onClick={decrease}>
          -1 감소
        </button>
      </div>
    </div>
  );
}
