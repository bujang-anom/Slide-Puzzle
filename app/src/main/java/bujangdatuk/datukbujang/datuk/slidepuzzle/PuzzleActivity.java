package bujangdatuk.datukbujang.datuk.slidepuzzle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import bujangdatuk.datukbujang.datuk.slidepuzzle.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PuzzleActivity extends AppCompatActivity {
    float x1,x2;
    float y1,y2;
    String swipe_direction;
    Integer grid_target;
    Integer grid_origin;
    Integer counter_step = 0;
    private static final int SWIPE_MIN_DISTANCE = 120;
    Map<Integer, Integer> mapImg = new HashMap<Integer, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        //initPuzzle();
        hideLabel();
    }

    private void hideLabel(){
        TextView textView = (TextView)findViewById(R.id.puzzle_solve_title);
        textView.setVisibility(View.GONE);

        textView = (TextView)findViewById(R.id.counter_step);
        textView.setVisibility(View.GONE);

        textView = (TextView)findViewById(R.id.counter_step_label);
        textView.setVisibility(View.GONE);

    }

    private void initPuzzle(){
        //init view

        //vertical line
        View view = (View)findViewById(R.id.vertical_line_1);
        view.setVisibility(View.VISIBLE);
        view = (View)findViewById(R.id.vertical_line_2);
        view.setVisibility(View.VISIBLE);
        view = (View)findViewById(R.id.vertical_line_3);
        view.setVisibility(View.VISIBLE);
        view = (View)findViewById(R.id.vertical_line_4);
        view.setVisibility(View.VISIBLE);
        view = (View)findViewById(R.id.vertical_line_5);
        view.setVisibility(View.VISIBLE);
        view = (View)findViewById(R.id.vertical_line_6);
        view.setVisibility(View.VISIBLE);
        //horizontal
        view = (View)findViewById(R.id.horizontal_line_1);
        view.setVisibility(View.VISIBLE);
        view = (View)findViewById(R.id.horizontal_line_2);
        view.setVisibility(View.VISIBLE);


        //init random image puzzle
        List<Integer> b = new ArrayList<Integer>();
        List<Integer> c = new ArrayList<Integer>();
        for(int i=0; i<9; i++){
            b.add(i);
            c.add(i);
        }

        do{
            Collections.shuffle(b);
            Collections.shuffle(c);
            Integer[] arrayB = b.toArray(new Integer[b.size()]);
            Integer[] arrayC = c.toArray(new Integer[c.size()]);

            Map<Integer, Integer> mapRes = new HashMap<Integer, Integer>();
            mapRes.put(arrayB[0], R.drawable.monas_1_1);
            mapRes.put(arrayB[1], R.drawable.monas_1_2);
            mapRes.put(arrayB[2], R.drawable.monas_1_3);
            mapRes.put(arrayB[3], R.drawable.monas_2_1);
            mapRes.put(arrayB[4], R.drawable.monas_2_2);
            mapRes.put(arrayB[5], R.drawable.monas_2_3);
            mapRes.put(arrayB[6], R.drawable.monas_3_1);
            mapRes.put(arrayB[7], R.drawable.monas_3_2);
            mapRes.put(arrayB[8], R.drawable.monas_empty);


            mapImg.put(arrayC[0], R.id.grid_1_1);
            mapImg.put(arrayC[1], R.id.grid_1_2);
            mapImg.put(arrayC[2], R.id.grid_1_3);
            mapImg.put(arrayC[3], R.id.grid_2_1);
            mapImg.put(arrayC[4], R.id.grid_2_2);
            mapImg.put(arrayC[5], R.id.grid_2_3);
            mapImg.put(arrayC[6], R.id.grid_3_1);
            mapImg.put(arrayC[7], R.id.grid_3_2);
            mapImg.put(arrayC[8], R.id.grid_3_3);

            for (int i=0;i<9;i++) {
                ImageView imageView = (ImageView)findViewById(mapImg.get(i));
                imageView.setImageResource(mapRes.get(b.get(i)));
                imageView.setTag(mapRes.get(b.get(i)));
            }
        }while (!isSolvable());

        addPuzzleTouchListener();

        TextView textView = (TextView)findViewById(R.id.puzzle_solve_title);
        textView.setVisibility(View.GONE);

        textView = (TextView)findViewById(R.id.counter_step);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 60);

        Button button_reset = (Button)findViewById(R.id.button_reset);
        button_reset.setText("RESET");
        button_reset.setVisibility(View.GONE);

    }

    private void addPuzzleTouchListener(){
        for (int i=0;i<9;i++) {
            ImageView imageView = (ImageView) findViewById(mapImg.get(i));
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    grid_origin = v.getId();
                    return onTouchEvent(event);

                }
            });
        }
    }

    private void removePuzzleTouchListener(){
        for(int i=0;i<9;i++){
            ImageView imageView = (ImageView) findViewById(mapImg.get(i));
            imageView.setClickable(false);
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
    }

    //event detect swipe
    public boolean onTouchEvent(MotionEvent touchevent)
    {
        switch (touchevent.getAction())
        {
            // when user first touches the screen we get x and y coordinate
            case MotionEvent.ACTION_DOWN:
            {
                x1 = touchevent.getX();
                y1 = touchevent.getY();
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                x2 = touchevent.getX();
                y2 = touchevent.getY();
                boolean status_swipe = false;
                //if left to right sweep event on screen
                if ((x2 - x1)>SWIPE_MIN_DISTANCE)
                {
                    swipe_direction = "RIGHT";
                    status_swipe = true;
                }

                // if right to left sweep event on screen
                if ((x1 - x2) > SWIPE_MIN_DISTANCE)
                {
                    swipe_direction = "LEFT";
                    status_swipe = true;
                }

                // if UP to Down sweep event on screen
                if ((y2 - y1) > SWIPE_MIN_DISTANCE)
                {
                    swipe_direction = "DOWN";
                    status_swipe = true;
                }

                //if Down to UP sweep event on screen
                if ((y1 - y2) > SWIPE_MIN_DISTANCE)
                {
                    swipe_direction = "UP";
                    status_swipe = true;
                }

                if(status_swipe == true){
                    grid_target = getTargetGrid(grid_origin, swipe_direction);


                    if(isBlankGrid(grid_target)){
                        ImageView imageViewOrigin = (ImageView)findViewById(grid_origin);
                        Integer tag_origin = (Integer)imageViewOrigin.getTag();
                        ImageView imageViewTarget = (ImageView)findViewById(grid_target);
                        Integer tag_target = (Integer)imageViewTarget.getTag();

                        imageViewOrigin.setImageResource(tag_target);
                        imageViewOrigin.setTag(tag_target);
                        imageViewTarget.setImageResource(tag_origin);
                        imageViewTarget.setTag(tag_origin);
                        counter_step++;
                        showCounterStep();

                        if(isSolve()){
                            solveState();

                        }
                    }
                    else{
                        Toast.makeText(PuzzleActivity.this, "Please move toward empty grid", Toast.LENGTH_LONG).show();
                    }

                }

                break;
            }
        }
        return false;
    }

    public void reset(View view){
        initPuzzle();
        counter_step = 0;
        showCounterStep();
    }

    private void solveState(){
        //Toast.makeText(PuzzleActivity.this, "Puzzle Solve!", Toast.LENGTH_LONG).show();
        removePuzzleTouchListener();
        ImageView imageView = (ImageView)findViewById(R.id.grid_3_3);
        imageView.setImageResource(R.drawable.monas_3_3);

        //vertical line
        View view = (View)findViewById(R.id.vertical_line_1);
        view.setVisibility(View.GONE);
        view = (View)findViewById(R.id.vertical_line_2);
        view.setVisibility(View.GONE);
        view = (View)findViewById(R.id.vertical_line_3);
        view.setVisibility(View.GONE);
        view = (View)findViewById(R.id.vertical_line_4);
        view.setVisibility(View.GONE);
        view = (View)findViewById(R.id.vertical_line_5);
        view.setVisibility(View.GONE);
        view = (View)findViewById(R.id.vertical_line_6);
        view.setVisibility(View.GONE);
        //horizontal
        view = (View)findViewById(R.id.horizontal_line_1);
        view.setVisibility(View.GONE);
        view = (View)findViewById(R.id.horizontal_line_2);
        view.setVisibility(View.GONE);

        TextView textView = (TextView)findViewById(R.id.puzzle_solve_title);
        textView.setVisibility(View.VISIBLE);

        textView = (TextView)findViewById(R.id.counter_step);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 34);

        Button button_reset = (Button)findViewById(R.id.button_reset);
        button_reset.setVisibility(View.VISIBLE);

    }

    public void showCounterStep(){
        TextView textView = (TextView)findViewById(R.id.counter_step);
        textView.setVisibility(View.VISIBLE);
        textView.setText("" + counter_step);

        textView = (TextView)findViewById(R.id.counter_step_label);
        textView.setVisibility(View.VISIBLE);
    }

    /*
        Input : ID imageView
        Output : true if imageView is blank
     */
    private boolean isBlankGrid(Integer idImageView){
        if (idImageView == 0) return false;
        ImageView imageView = (ImageView)findViewById(idImageView);
        return imageView.getTag().equals(R.drawable.monas_empty);
    }

    /*
    return true if number is even
     */
    private boolean isEven(Integer number){
        return ((number % 2)==0);
    }

    private Integer gridPoint(Integer image_tag){
        Integer point = 0;
        switch (image_tag){
            case R.drawable.monas_3_2 :
                point = 8;
                break;
            case R.drawable.monas_3_1 :
                point = 7;
                break;
            case R.drawable.monas_2_3 :
                point = 6;
                break;
            case R.drawable.monas_2_2 :
                point = 5;
                break;
            case R.drawable.monas_2_1 :
                point = 4;
                break;
            case R.drawable.monas_1_3 :
                point = 3;
                break;
            case R.drawable.monas_1_2 :
                point = 2;
                break;
            case R.drawable.monas_1_1 :
                point = 1;
                break;
            default:
                point=0;
        }
        return point;
    }
    /*
    return true if grid position after random is solvable
    */
    private boolean isSolvable(){
        Integer[] array_point;
        array_point = new Integer[9];
        ImageView imageViewGrid1_1 = (ImageView)findViewById(R.id.grid_1_1);
        array_point[0] = gridPoint((Integer)imageViewGrid1_1.getTag());
        ImageView imageViewGrid1_2 = (ImageView)findViewById(R.id.grid_1_2);
        array_point[1] = gridPoint((Integer)imageViewGrid1_2.getTag());
        ImageView imageViewGrid1_3 = (ImageView)findViewById(R.id.grid_1_3);
        array_point[2] = gridPoint((Integer)imageViewGrid1_3.getTag());
        ImageView imageViewGrid2_1 = (ImageView)findViewById(R.id.grid_2_1);
        array_point[3] = gridPoint((Integer)imageViewGrid2_1.getTag());
        ImageView imageViewGrid2_2 = (ImageView)findViewById(R.id.grid_2_2);
        array_point[4] = gridPoint((Integer)imageViewGrid2_2.getTag());
        ImageView imageViewGrid2_3 = (ImageView)findViewById(R.id.grid_2_3);
        array_point[5] = gridPoint((Integer)imageViewGrid2_3.getTag());
        ImageView imageViewGrid3_1 = (ImageView)findViewById(R.id.grid_3_1);
        array_point[6] = gridPoint((Integer)imageViewGrid3_1.getTag());
        ImageView imageViewGrid3_2 = (ImageView)findViewById(R.id.grid_3_2);
        array_point[7] = gridPoint((Integer)imageViewGrid3_2.getTag());
        ImageView imageViewGrid3_3 = (ImageView)findViewById(R.id.grid_3_3);
        array_point[8] = gridPoint((Integer)imageViewGrid3_3.getTag());

        Integer invertion_point = 0;
        for(int i=0;i<8;i++){
            for(int j=(i+1);j<9;j++){
                if((array_point[i] > array_point[j])&&(array_point[j] != 0)){
                    invertion_point++;
                }
            }
        }
        return isEven(invertion_point);
    }

    /*
        output : true if all image in correct grid
     */
    private boolean isSolve(){
        ImageView imageViewGrid1_1 = (ImageView)findViewById(R.id.grid_1_1);
        ImageView imageViewGrid1_2 = (ImageView)findViewById(R.id.grid_1_2);
        ImageView imageViewGrid1_3 = (ImageView)findViewById(R.id.grid_1_3);
        ImageView imageViewGrid2_1 = (ImageView)findViewById(R.id.grid_2_1);
        ImageView imageViewGrid2_2 = (ImageView)findViewById(R.id.grid_2_2);
        ImageView imageViewGrid2_3 = (ImageView)findViewById(R.id.grid_2_3);
        ImageView imageViewGrid3_1 = (ImageView)findViewById(R.id.grid_3_1);
        ImageView imageViewGrid3_2 = (ImageView)findViewById(R.id.grid_3_2);
        ImageView imageViewGrid3_3 = (ImageView)findViewById(R.id.grid_3_3);

        return (
                (imageViewGrid1_1.getTag().equals(R.drawable.monas_1_1)) &&
                (imageViewGrid1_2.getTag().equals(R.drawable.monas_1_2)) &&
                (imageViewGrid1_3.getTag().equals(R.drawable.monas_1_3)) &&
                (imageViewGrid2_1.getTag().equals(R.drawable.monas_2_1)) &&
                (imageViewGrid2_2.getTag().equals(R.drawable.monas_2_2)) &&
                (imageViewGrid2_3.getTag().equals(R.drawable.monas_2_3)) &&
                (imageViewGrid3_1.getTag().equals(R.drawable.monas_3_1)) &&
                (imageViewGrid3_2.getTag().equals(R.drawable.monas_3_2)) &&
                (imageViewGrid3_3.getTag().equals(R.drawable.monas_empty))
                );

    }

    /*
        Input :
        param 1 : ID ImageView origin grid
        param 2: arah swipe
        Output : ID ImageView target grid
        return 0 artinya cant move

     */
    private Integer getTargetGrid(Integer origin_grid, String swipe_direction){
        Integer target_grid = 0;
        switch (origin_grid){
            case R.id.grid_1_1 :
                switch (swipe_direction){
                    case "RIGHT" :
                        target_grid = R.id.grid_1_2;
                        break;
                    case "DOWN" :
                        target_grid = R.id.grid_2_1;
                        break;
                    default: target_grid = 0;
                }
                break;
            case R.id.grid_1_2 :
                switch (swipe_direction){
                    case "RIGHT" :
                        target_grid = R.id.grid_1_3;
                        break;
                    case "DOWN" :
                        target_grid = R.id.grid_2_2;
                        break;
                    case "LEFT" :
                        target_grid = R.id.grid_1_1;
                        break;
                    default: target_grid = 0;
                }
                break;
            case R.id.grid_1_3 :
                switch (swipe_direction){
                    case "DOWN" :
                        target_grid = R.id.grid_2_3;
                        break;
                    case "LEFT" :
                        target_grid = R.id.grid_1_2;
                        break;
                    default: target_grid = 0;
                }
                break;
            case R.id.grid_2_1 :
                switch (swipe_direction){
                    case "UP" :
                        target_grid = R.id.grid_1_1 ;
                        break;
                    case "DOWN" :
                        target_grid = R.id.grid_3_1;
                        break;
                    case "RIGHT" :
                        target_grid = R.id.grid_2_2;
                        break;
                    default: target_grid = 0;
                }
                break;
            case R.id.grid_2_2 :
                switch (swipe_direction){
                    case "UP" :
                        target_grid = R.id.grid_1_2 ;
                        break;
                    case "DOWN" :
                        target_grid = R.id.grid_3_2;
                        break;
                    case "RIGHT" :
                        target_grid = R.id.grid_2_3;
                        break;
                    case "LEFT" :
                        target_grid = R.id.grid_2_1;
                        break;
                    default: target_grid = 0;
                }
                break;
            case R.id.grid_2_3 :
                switch (swipe_direction){
                    case "UP" :
                        target_grid = R.id.grid_1_3 ;
                        break;
                    case "DOWN" :
                        target_grid = R.id.grid_3_3;
                        break;
                    case "LEFT" :
                        target_grid = R.id.grid_2_2;
                        break;
                    default: target_grid = 0;
                }
                break;
            case R.id.grid_3_1 :
                switch (swipe_direction){
                    case "UP" :
                        target_grid = R.id.grid_2_1 ;
                        break;
                    case "RIGHT" :
                        target_grid = R.id.grid_3_2;
                        break;
                    default: target_grid = 0;
                }
                break;
            case R.id.grid_3_2 :
                switch (swipe_direction){
                    case "UP" :
                        target_grid = R.id.grid_2_2 ;
                        break;
                    case "RIGHT" :
                        target_grid = R.id.grid_3_3;
                        break;
                    case "LEFT" :
                        target_grid = R.id.grid_3_1;
                        break;
                    default: target_grid = 0;
                }
                break;
            case R.id.grid_3_3 :
                switch (swipe_direction){
                    case "UP" :
                        target_grid = R.id.grid_2_3 ;
                        break;
                    case "LEFT" :
                        target_grid = R.id.grid_3_2;
                        break;
                    default: target_grid = 0;
                }
                break;
        }

        return target_grid;
    }


}

