using System;
using System.Collections.Generic;
using System.Diagnostics;
using NUnit.Framework;

namespace CollectionTest
{
    [TestFixture]
    public class AddIterateRemoveTest
    {
        private const int RUNS_TIME_MS = 10*1000;
        private const int SIZE = 1000*1000;
        private static readonly int[] INTS = new int[SIZE];

        static AddIterateRemoveTest()
        {
            for (int i = 0; i < SIZE; i++)
                INTS[i] = i;
        }

        [Test]
        public static void PerfomanceTest()
        {
            Test(new List<int>());
            Test(new LinkedList<int>());
            Test(new HashSet<int>());
            Test(new SortedSet<int>());
        }

        private static void Test(ICollection<int> ints)
        {
            Test(ints, ints.GetType().Name);
        }

        private static void Test(ICollection<int> ints, string name)
        {
            Stopwatch sw = new Stopwatch();

            for (int size = SIZE; size >= 10; size /= 10)
            {
                long adding = 0, removing = 0, iterating = 0, searching = 0;

                var runs = 0;
                long endTime = Environment.TickCount + RUNS_TIME_MS;
                do
                {
                    runs++;

                    sw.Start();
                    TestAdd(ints, size);
                    sw.Stop();

                    adding += Convert.ToInt64(sw.Elapsed.TotalMilliseconds * 1000);

                    sw.Restart();
                    TestIterate(ints);
                    sw.Stop();
                    iterating += Convert.ToInt64(sw.Elapsed.TotalMilliseconds * 1000);

                    sw.Restart();
                    TestSearch(ints);
                    sw.Stop();
                    searching += Convert.ToInt64(sw.Elapsed.TotalMilliseconds * 1000);

                    sw.Restart();
                    TestRemove(ints, size);
                    sw.Stop();
                    removing += Convert.ToInt64(sw.Elapsed.TotalMilliseconds * 1000);

                    ints.Clear();
                } while (endTime > Environment.TickCount);

                String result = "<tr><td>" + name + "</td><td aligned=\"right\">" + String.Format("{0}", size)
                                + "</td><td aligned=\"right\">" + Format(10*adding/runs)
                                + "</td><td aligned=\"right\">" + Format(iterating/runs)
                                + "</td><td aligned=\"right\">" + Format(10*searching/runs)
                                + "</td><td aligned=\"right\">" + Format(10*removing/runs)
                                + "</td></tr>";

                Debug.WriteLine(result);
            }


        }

        private static void TestRemove(ICollection<int> ints, int size)
        {
            for (int i = 0; i < size - 1; i++)
            {
                ints.Remove(INTS[i]);
            }
        }

        private static bool TestSearch(ICollection<int> ints)
        {
            int searchEl = ints.Count - 1;
            return ints.Contains(searchEl);
        }

        private static long TestIterate(ICollection<int> ints)
        {
            long sum = 0;
            foreach (int i in ints)
            {
                sum += i;
            }
            return sum;
        }


        private static void TestAdd(ICollection<int> ints, int size)
        {
            for (int i = 0; i < size; i++)
            {
                ints.Add(INTS[i]);
            }
        }

        private static string Format(long l)
        {
            return l < 1000
                ? "" + (l/10.0)
                : l < 10000
                    ? "" + l/10
                    : String.Format("{0}", l/10);
        }
    }
}
