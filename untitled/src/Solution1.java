public class Solution1 {
    public static void main(String[] args) {
        int w = 8;
        int h = 12;
        System.out.println(solution(w, h));
    }
    public static long solution(int w,int h) {
        double com = (double)h/(double)w;

        int width = 1;
        int height = 1;
        long sum = 1;

        while(true) {
            double y = Math.abs(com - ((double)(height+1)/(double)width));
            double x = Math.abs(com - ((double)height / (double)(width+1)));
            double xy = Math.abs(com - ((double)height / (double)width));

            if(xy == 0.0) {
                break;
            }else {
                if(y < x) {
                    height++;
                    sum++;
                }else {
                    width++;
                    sum++;
                }
            }

        }

        sum = (w/width)*sum;

        long answer = (w*h)-sum;
        return answer;
    }
}