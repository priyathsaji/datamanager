package com.cdm.priyathsaji.datamanager;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

/*
 * Created by PRIYATH SAJI on 16-11-2016.
 */
public class wifiService extends Service {
    public Handler handler = new Handler();
    long wPrevious,wNow,wSpeed,wpData,wpUploaded;
    static long wnData,wnUploaded;
    static int kbps=0,mbps=0,flag = 0;
    public static boolean refreshed;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    Calendar c = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

    String initialDate,currentdate;
    dataHolder data;

    int ch;
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public int onStartCommand(Intent intent, int flags, int startId){
        c = Calendar.getInstance();
        initialDate = df.format(c.getTime());
        //updatewpData();
        wPrevious = TrafficStats.getTotalRxBytes()-TrafficStats.getMobileRxBytes();
        wNow = wPrevious;
        data = getDataHolder();
        startRun();

        return START_STICKY;
    }

    int kbps_array[]={R.drawable.ic_0, R.drawable.ic_1, R.drawable.ic_2, R.drawable.ic_3, R.drawable.ic_4, R.drawable.ic_5, R.drawable.ic_6, R.drawable.ic_7, R.drawable.ic_8, R.drawable.ic_9, R.drawable.ic_10, R.drawable.ic_11, R.drawable.ic_12, R.drawable.ic_13, R.drawable.ic_14, R.drawable.ic_15, R.drawable.ic_16, R.drawable.ic_16, R.drawable.ic_17, R.drawable.ic_18, R.drawable.ic_19, R.drawable.ic_20, R.drawable.ic_21, R.drawable.ic_22, R.drawable.ic_23, R.drawable.ic_23, R.drawable.ic_24, R.drawable.ic_25, R.drawable.ic_26, R.drawable.ic_27, R.drawable.ic_28, R.drawable.ic_29, R.drawable.ic_30, R.drawable.ic_31, R.drawable.ic_32, R.drawable.ic_33, R.drawable.ic_34, R.drawable.ic_35, R.drawable.ic_36, R.drawable.ic_37, R.drawable.ic_38, R.drawable.ic_39, R.drawable.ic_40, R.drawable.ic_41, R.drawable.ic_42, R.drawable.ic_43, R.drawable.ic_44, R.drawable.ic_45, R.drawable.ic_46, R.drawable.ic_47, R.drawable.ic_48, R.drawable.ic_49, R.drawable.ic_50,
            R.drawable.ic_51, R.drawable.ic_52, R.drawable.ic_53, R.drawable.ic_54, R.drawable.ic_55, R.drawable.ic_56, R.drawable.ic_57, R.drawable.ic_58, R.drawable.ic_59, R.drawable.ic_60, R.drawable.ic_61, R.drawable.ic_62, R.drawable.ic_63, R.drawable.ic_64, R.drawable.ic_65, R.drawable.ic_66, R.drawable.ic_67, R.drawable.ic_68, R.drawable.ic_69, R.drawable.ic_70, R.drawable.ic_71, R.drawable.ic_72, R.drawable.ic_73, R.drawable.ic_74, R.drawable.ic_75, R.drawable.ic_76, R.drawable.ic_77, R.drawable.ic_78, R.drawable.ic_79, R.drawable.ic_80, R.drawable.ic_81, R.drawable.ic_82, R.drawable.ic_83, R.drawable.ic_84, R.drawable.ic_85, R.drawable.ic_86, R.drawable.ic_87, R.drawable.ic_88, R.drawable.ic_89, R.drawable.ic_90, R.drawable.ic_91, R.drawable.ic_92, R.drawable.ic_93, R.drawable.ic_94, R.drawable.ic_95, R.drawable.ic_96, R.drawable.ic_97, R.drawable.ic_98, R.drawable.ic_99, R.drawable.ic_100,
            R.drawable.ic_101, R.drawable.ic_102, R.drawable.ic_103, R.drawable.ic_104, R.drawable.ic_105, R.drawable.ic_106, R.drawable.ic_107, R.drawable.ic_108, R.drawable.ic_109, R.drawable.ic_110, R.drawable.ic_111, R.drawable.ic_112, R.drawable.ic_113, R.drawable.ic_114, R.drawable.ic_115, R.drawable.ic_116, R.drawable.ic_117, R.drawable.ic_118, R.drawable.ic_119, R.drawable.ic_120, R.drawable.ic_121, R.drawable.ic_122, R.drawable.ic_123, R.drawable.ic_124, R.drawable.ic_125, R.drawable.ic_126, R.drawable.ic_127, R.drawable.ic_128, R.drawable.ic_129, R.drawable.ic_130, R.drawable.ic_131, R.drawable.ic_132, R.drawable.ic_133, R.drawable.ic_134, R.drawable.ic_135, R.drawable.ic_136, R.drawable.ic_137, R.drawable.ic_138, R.drawable.ic_139, R.drawable.ic_140, R.drawable.ic_141, R.drawable.ic_142, R.drawable.ic_143, R.drawable.ic_144, R.drawable.ic_145, R.drawable.ic_146, R.drawable.ic_147, R.drawable.ic_148, R.drawable.ic_149, R.drawable.ic_150,
            R.drawable.ic_151, R.drawable.ic_152, R.drawable.ic_153, R.drawable.ic_154, R.drawable.ic_155, R.drawable.ic_156, R.drawable.ic_157, R.drawable.ic_158, R.drawable.ic_159, R.drawable.ic_160, R.drawable.ic_161, R.drawable.ic_162, R.drawable.ic_163, R.drawable.ic_164, R.drawable.ic_165, R.drawable.ic_166, R.drawable.ic_167, R.drawable.ic_168, R.drawable.ic_169, R.drawable.ic_170, R.drawable.ic_171, R.drawable.ic_172, R.drawable.ic_173, R.drawable.ic_174, R.drawable.ic_175, R.drawable.ic_176, R.drawable.ic_177, R.drawable.ic_178, R.drawable.ic_179, R.drawable.ic_180, R.drawable.ic_181, R.drawable.ic_182, R.drawable.ic_183, R.drawable.ic_184, R.drawable.ic_185, R.drawable.ic_186, R.drawable.ic_187, R.drawable.ic_188, R.drawable.ic_189, R.drawable.ic_190, R.drawable.ic_191, R.drawable.ic_192, R.drawable.ic_193, R.drawable.ic_194, R.drawable.ic_195, R.drawable.ic_196, R.drawable.ic_197, R.drawable.ic_198, R.drawable.ic_199, R.drawable.ic_200,
            R.drawable.ic_201, R.drawable.ic_202, R.drawable.ic_203, R.drawable.ic_204, R.drawable.ic_205, R.drawable.ic_206, R.drawable.ic_207, R.drawable.ic_208, R.drawable.ic_209, R.drawable.ic_210, R.drawable.ic_211, R.drawable.ic_212, R.drawable.ic_213, R.drawable.ic_214, R.drawable.ic_215, R.drawable.ic_216, R.drawable.ic_217, R.drawable.ic_218, R.drawable.ic_219, R.drawable.ic_220, R.drawable.ic_221, R.drawable.ic_222, R.drawable.ic_223, R.drawable.ic_224, R.drawable.ic_225, R.drawable.ic_226, R.drawable.ic_227, R.drawable.ic_228, R.drawable.ic_229, R.drawable.ic_230, R.drawable.ic_231, R.drawable.ic_232, R.drawable.ic_233, R.drawable.ic_234, R.drawable.ic_235, R.drawable.ic_236, R.drawable.ic_237, R.drawable.ic_238, R.drawable.ic_239, R.drawable.ic_240, R.drawable.ic_241, R.drawable.ic_242, R.drawable.ic_243, R.drawable.ic_244, R.drawable.ic_245, R.drawable.ic_246, R.drawable.ic_247, R.drawable.ic_248, R.drawable.ic_249, R.drawable.ic_250,
            R.drawable.ic_251, R.drawable.ic_252, R.drawable.ic_253, R.drawable.ic_254, R.drawable.ic_255, R.drawable.ic_256, R.drawable.ic_257, R.drawable.ic_258, R.drawable.ic_259, R.drawable.ic_260, R.drawable.ic_261, R.drawable.ic_262, R.drawable.ic_263, R.drawable.ic_264, R.drawable.ic_265, R.drawable.ic_266, R.drawable.ic_267, R.drawable.ic_268, R.drawable.ic_269, R.drawable.ic_270, R.drawable.ic_271, R.drawable.ic_272, R.drawable.ic_273, R.drawable.ic_274, R.drawable.ic_275, R.drawable.ic_276, R.drawable.ic_277, R.drawable.ic_278, R.drawable.ic_279, R.drawable.ic_280, R.drawable.ic_281, R.drawable.ic_282, R.drawable.ic_283, R.drawable.ic_284, R.drawable.ic_285, R.drawable.ic_286, R.drawable.ic_287, R.drawable.ic_288, R.drawable.ic_289, R.drawable.ic_290, R.drawable.ic_291, R.drawable.ic_292, R.drawable.ic_293, R.drawable.ic_294, R.drawable.ic_295, R.drawable.ic_296, R.drawable.ic_297, R.drawable.ic_298, R.drawable.ic_299, R.drawable.ic_300,
            R.drawable.ic_301, R.drawable.ic_302, R.drawable.ic_303, R.drawable.ic_304, R.drawable.ic_305, R.drawable.ic_306, R.drawable.ic_307, R.drawable.ic_308, R.drawable.ic_309, R.drawable.ic_310, R.drawable.ic_311, R.drawable.ic_312, R.drawable.ic_313, R.drawable.ic_314, R.drawable.ic_315, R.drawable.ic_316, R.drawable.ic_317, R.drawable.ic_318, R.drawable.ic_319, R.drawable.ic_320, R.drawable.ic_321, R.drawable.ic_322, R.drawable.ic_323, R.drawable.ic_324, R.drawable.ic_325, R.drawable.ic_326, R.drawable.ic_327, R.drawable.ic_328, R.drawable.ic_329, R.drawable.ic_330, R.drawable.ic_331, R.drawable.ic_332, R.drawable.ic_333, R.drawable.ic_334, R.drawable.ic_335, R.drawable.ic_336, R.drawable.ic_337, R.drawable.ic_338, R.drawable.ic_339, R.drawable.ic_340, R.drawable.ic_341, R.drawable.ic_342, R.drawable.ic_343, R.drawable.ic_344, R.drawable.ic_345, R.drawable.ic_346, R.drawable.ic_347, R.drawable.ic_348, R.drawable.ic_349, R.drawable.ic_350,
            R.drawable.ic_351, R.drawable.ic_352, R.drawable.ic_353, R.drawable.ic_354, R.drawable.ic_355, R.drawable.ic_356, R.drawable.ic_357, R.drawable.ic_358, R.drawable.ic_359, R.drawable.ic_360, R.drawable.ic_361, R.drawable.ic_362, R.drawable.ic_363, R.drawable.ic_364, R.drawable.ic_365, R.drawable.ic_365, R.drawable.ic_367, R.drawable.ic_368, R.drawable.ic_369, R.drawable.ic_370, R.drawable.ic_371, R.drawable.ic_372, R.drawable.ic_373, R.drawable.ic_374, R.drawable.ic_375, R.drawable.ic_376, R.drawable.ic_377, R.drawable.ic_378, R.drawable.ic_379, R.drawable.ic_380, R.drawable.ic_381, R.drawable.ic_382, R.drawable.ic_383, R.drawable.ic_384, R.drawable.ic_385, R.drawable.ic_386, R.drawable.ic_387, R.drawable.ic_388, R.drawable.ic_389, R.drawable.ic_390, R.drawable.ic_391, R.drawable.ic_392, R.drawable.ic_393, R.drawable.ic_394, R.drawable.ic_395, R.drawable.ic_396, R.drawable.ic_397, R.drawable.ic_398, R.drawable.ic_399, R.drawable.ic_400,
            R.drawable.ic_401, R.drawable.ic_402, R.drawable.ic_403, R.drawable.ic_404, R.drawable.ic_405, R.drawable.ic_406, R.drawable.ic_407, R.drawable.ic_408, R.drawable.ic_409, R.drawable.ic_410, R.drawable.ic_411, R.drawable.ic_412, R.drawable.ic_413, R.drawable.ic_414, R.drawable.ic_415, R.drawable.ic_416, R.drawable.ic_417, R.drawable.ic_418, R.drawable.ic_419, R.drawable.ic_420, R.drawable.ic_421, R.drawable.ic_422, R.drawable.ic_423, R.drawable.ic_424, R.drawable.ic_425, R.drawable.ic_426, R.drawable.ic_427, R.drawable.ic_428, R.drawable.ic_429, R.drawable.ic_430, R.drawable.ic_431, R.drawable.ic_432, R.drawable.ic_433, R.drawable.ic_434, R.drawable.ic_435, R.drawable.ic_436, R.drawable.ic_437, R.drawable.ic_438, R.drawable.ic_439, R.drawable.ic_440, R.drawable.ic_441, R.drawable.ic_442, R.drawable.ic_443, R.drawable.ic_444, R.drawable.ic_445, R.drawable.ic_446, R.drawable.ic_447, R.drawable.ic_448, R.drawable.ic_449, R.drawable.ic_450,
            R.drawable.ic_451, R.drawable.ic_452, R.drawable.ic_453, R.drawable.ic_454, R.drawable.ic_455, R.drawable.ic_456, R.drawable.ic_457, R.drawable.ic_458, R.drawable.ic_459, R.drawable.ic_460, R.drawable.ic_461, R.drawable.ic_462, R.drawable.ic_463, R.drawable.ic_464, R.drawable.ic_465, R.drawable.ic_466, R.drawable.ic_467, R.drawable.ic_468, R.drawable.ic_469, R.drawable.ic_470, R.drawable.ic_471, R.drawable.ic_472, R.drawable.ic_473, R.drawable.ic_474, R.drawable.ic_475, R.drawable.ic_476, R.drawable.ic_477, R.drawable.ic_478, R.drawable.ic_479, R.drawable.ic_480, R.drawable.ic_481, R.drawable.ic_482, R.drawable.ic_483, R.drawable.ic_484, R.drawable.ic_485, R.drawable.ic_486, R.drawable.ic_487, R.drawable.ic_488, R.drawable.ic_489, R.drawable.ic_490, R.drawable.ic_491, R.drawable.ic_492, R.drawable.ic_493, R.drawable.ic_494, R.drawable.ic_495, R.drawable.ic_496, R.drawable.ic_497, R.drawable.ic_498, R.drawable.ic_499, R.drawable.ic_500,
            R.drawable.ic_501, R.drawable.ic_502, R.drawable.ic_503, R.drawable.ic_504, R.drawable.ic_505, R.drawable.ic_506, R.drawable.ic_507, R.drawable.ic_508, R.drawable.ic_509, R.drawable.ic_510, R.drawable.ic_511, R.drawable.ic_512, R.drawable.ic_513, R.drawable.ic_514, R.drawable.ic_515, R.drawable.ic_516, R.drawable.ic_517, R.drawable.ic_518, R.drawable.ic_519, R.drawable.ic_520, R.drawable.ic_521, R.drawable.ic_522, R.drawable.ic_523, R.drawable.ic_524, R.drawable.ic_525, R.drawable.ic_526, R.drawable.ic_527, R.drawable.ic_528, R.drawable.ic_529, R.drawable.ic_530, R.drawable.ic_531, R.drawable.ic_532, R.drawable.ic_533, R.drawable.ic_534, R.drawable.ic_535, R.drawable.ic_536, R.drawable.ic_537, R.drawable.ic_538, R.drawable.ic_539, R.drawable.ic_540, R.drawable.ic_541, R.drawable.ic_542, R.drawable.ic_543, R.drawable.ic_544, R.drawable.ic_545, R.drawable.ic_546, R.drawable.ic_547, R.drawable.ic_548, R.drawable.ic_549, R.drawable.ic_550,
            R.drawable.ic_551, R.drawable.ic_552, R.drawable.ic_553, R.drawable.ic_554, R.drawable.ic_555, R.drawable.ic_556, R.drawable.ic_557, R.drawable.ic_558, R.drawable.ic_559, R.drawable.ic_560, R.drawable.ic_561, R.drawable.ic_562, R.drawable.ic_563, R.drawable.ic_564, R.drawable.ic_565, R.drawable.ic_566, R.drawable.ic_567, R.drawable.ic_568, R.drawable.ic_569, R.drawable.ic_570, R.drawable.ic_571, R.drawable.ic_572, R.drawable.ic_573, R.drawable.ic_574, R.drawable.ic_575, R.drawable.ic_576, R.drawable.ic_577, R.drawable.ic_578, R.drawable.ic_579, R.drawable.ic_580, R.drawable.ic_581, R.drawable.ic_582, R.drawable.ic_583, R.drawable.ic_584, R.drawable.ic_585, R.drawable.ic_586, R.drawable.ic_587, R.drawable.ic_588, R.drawable.ic_589, R.drawable.ic_590, R.drawable.ic_591, R.drawable.ic_592, R.drawable.ic_593, R.drawable.ic_594, R.drawable.ic_595, R.drawable.ic_596, R.drawable.ic_597, R.drawable.ic_598, R.drawable.ic_599, R.drawable.ic_600,
            R.drawable.ic_601, R.drawable.ic_602, R.drawable.ic_603, R.drawable.ic_604, R.drawable.ic_605, R.drawable.ic_606, R.drawable.ic_607, R.drawable.ic_608, R.drawable.ic_609, R.drawable.ic_610, R.drawable.ic_611, R.drawable.ic_612, R.drawable.ic_613, R.drawable.ic_614, R.drawable.ic_615, R.drawable.ic_616, R.drawable.ic_617, R.drawable.ic_618, R.drawable.ic_619, R.drawable.ic_620, R.drawable.ic_621, R.drawable.ic_622, R.drawable.ic_623, R.drawable.ic_624, R.drawable.ic_625, R.drawable.ic_626, R.drawable.ic_627, R.drawable.ic_628, R.drawable.ic_629, R.drawable.ic_630, R.drawable.ic_631, R.drawable.ic_632, R.drawable.ic_633, R.drawable.ic_634, R.drawable.ic_635, R.drawable.ic_636, R.drawable.ic_637, R.drawable.ic_638, R.drawable.ic_639, R.drawable.ic_640, R.drawable.ic_641, R.drawable.ic_642, R.drawable.ic_643, R.drawable.ic_644, R.drawable.ic_645, R.drawable.ic_646, R.drawable.ic_647, R.drawable.ic_648, R.drawable.ic_649, R.drawable.ic_650,
            R.drawable.ic_651, R.drawable.ic_652, R.drawable.ic_653, R.drawable.ic_654, R.drawable.ic_655, R.drawable.ic_656, R.drawable.ic_657, R.drawable.ic_658, R.drawable.ic_659, R.drawable.ic_660, R.drawable.ic_661, R.drawable.ic_662, R.drawable.ic_663, R.drawable.ic_664, R.drawable.ic_665, R.drawable.ic_666, R.drawable.ic_667, R.drawable.ic_668, R.drawable.ic_669, R.drawable.ic_670, R.drawable.ic_671, R.drawable.ic_672, R.drawable.ic_673, R.drawable.ic_674, R.drawable.ic_675, R.drawable.ic_676, R.drawable.ic_677, R.drawable.ic_678, R.drawable.ic_679, R.drawable.ic_680, R.drawable.ic_681, R.drawable.ic_682, R.drawable.ic_683, R.drawable.ic_684, R.drawable.ic_685, R.drawable.ic_686, R.drawable.ic_687, R.drawable.ic_688, R.drawable.ic_689, R.drawable.ic_690, R.drawable.ic_691, R.drawable.ic_692, R.drawable.ic_693, R.drawable.ic_694, R.drawable.ic_695, R.drawable.ic_696, R.drawable.ic_697, R.drawable.ic_698, R.drawable.ic_699, R.drawable.ic_700,
            R.drawable.ic_701, R.drawable.ic_702, R.drawable.ic_703, R.drawable.ic_704, R.drawable.ic_705, R.drawable.ic_706, R.drawable.ic_707, R.drawable.ic_708, R.drawable.ic_709, R.drawable.ic_710, R.drawable.ic_711, R.drawable.ic_712, R.drawable.ic_713, R.drawable.ic_714, R.drawable.ic_715, R.drawable.ic_716, R.drawable.ic_717, R.drawable.ic_718, R.drawable.ic_719, R.drawable.ic_720, R.drawable.ic_721, R.drawable.ic_722, R.drawable.ic_723, R.drawable.ic_724, R.drawable.ic_725, R.drawable.ic_726, R.drawable.ic_727, R.drawable.ic_728, R.drawable.ic_729, R.drawable.ic_730, R.drawable.ic_731, R.drawable.ic_732, R.drawable.ic_733, R.drawable.ic_734, R.drawable.ic_735, R.drawable.ic_736, R.drawable.ic_737, R.drawable.ic_738, R.drawable.ic_739, R.drawable.ic_740, R.drawable.ic_741, R.drawable.ic_742, R.drawable.ic_743, R.drawable.ic_744, R.drawable.ic_745, R.drawable.ic_746, R.drawable.ic_747, R.drawable.ic_748, R.drawable.ic_749, R.drawable.ic_750,
            R.drawable.ic_751, R.drawable.ic_752, R.drawable.ic_753, R.drawable.ic_754, R.drawable.ic_755, R.drawable.ic_756, R.drawable.ic_757, R.drawable.ic_758, R.drawable.ic_759, R.drawable.ic_760, R.drawable.ic_761, R.drawable.ic_762, R.drawable.ic_763, R.drawable.ic_764, R.drawable.ic_765, R.drawable.ic_766, R.drawable.ic_767, R.drawable.ic_768, R.drawable.ic_769, R.drawable.ic_770, R.drawable.ic_771, R.drawable.ic_772, R.drawable.ic_773, R.drawable.ic_774, R.drawable.ic_775, R.drawable.ic_776, R.drawable.ic_777, R.drawable.ic_778, R.drawable.ic_779, R.drawable.ic_780, R.drawable.ic_781, R.drawable.ic_782, R.drawable.ic_783, R.drawable.ic_784, R.drawable.ic_785, R.drawable.ic_786, R.drawable.ic_787, R.drawable.ic_788, R.drawable.ic_789, R.drawable.ic_790, R.drawable.ic_791, R.drawable.ic_792, R.drawable.ic_793, R.drawable.ic_794, R.drawable.ic_795, R.drawable.ic_796, R.drawable.ic_797, R.drawable.ic_798, R.drawable.ic_799, R.drawable.ic_799,
            R.drawable.ic_801, R.drawable.ic_802, R.drawable.ic_803, R.drawable.ic_804, R.drawable.ic_805, R.drawable.ic_806, R.drawable.ic_807, R.drawable.ic_808, R.drawable.ic_809, R.drawable.ic_810, R.drawable.ic_811, R.drawable.ic_812, R.drawable.ic_813, R.drawable.ic_814, R.drawable.ic_815, R.drawable.ic_816, R.drawable.ic_817, R.drawable.ic_818, R.drawable.ic_819, R.drawable.ic_820, R.drawable.ic_821, R.drawable.ic_822, R.drawable.ic_823, R.drawable.ic_824, R.drawable.ic_825, R.drawable.ic_826, R.drawable.ic_827, R.drawable.ic_828, R.drawable.ic_829, R.drawable.ic_830, R.drawable.ic_831, R.drawable.ic_832, R.drawable.ic_833, R.drawable.ic_834, R.drawable.ic_835, R.drawable.ic_836, R.drawable.ic_837, R.drawable.ic_838, R.drawable.ic_839, R.drawable.ic_840, R.drawable.ic_841, R.drawable.ic_842, R.drawable.ic_843, R.drawable.ic_844, R.drawable.ic_845, R.drawable.ic_846, R.drawable.ic_847, R.drawable.ic_848, R.drawable.ic_849, R.drawable.ic_850,
            R.drawable.ic_851, R.drawable.ic_852, R.drawable.ic_853};

    int mbps_array[][]={{R.drawable.ic_0_9, R.drawable.ic_0_9, R.drawable.ic_0_9, R.drawable.ic_0_9, R.drawable.ic_0_9, R.drawable.ic_0_9, R.drawable.ic_0_9, R.drawable.ic_0_9, R.drawable.ic_0_9, R.drawable.ic_0_9},
            {R.drawable.ic_1_0, R.drawable.ic_1_1, R.drawable.ic_1_2, R.drawable.ic_1_3, R.drawable.ic_1_4, R.drawable.ic_1_5, R.drawable.ic_1_6, R.drawable.ic_1_7, R.drawable.ic_1_8, R.drawable.ic_1_9},
            {R.drawable.ic_2_0, R.drawable.ic_2_1, R.drawable.ic_2_2, R.drawable.ic_2_3, R.drawable.ic_2_4, R.drawable.ic_2_5, R.drawable.ic_2_6, R.drawable.ic_2_7, R.drawable.ic_2_8, R.drawable.ic_2_9},
            {R.drawable.ic_3_0, R.drawable.ic_3_1, R.drawable.ic_3_2, R.drawable.ic_3_3, R.drawable.ic_3_4, R.drawable.ic_3_5, R.drawable.ic_3_6, R.drawable.ic_3_7, R.drawable.ic_3_8, R.drawable.ic_3_9},
            {R.drawable.ic_4_0, R.drawable.ic_4_1, R.drawable.ic_4_2, R.drawable.ic_4_3, R.drawable.ic_4_4, R.drawable.ic_4_5, R.drawable.ic_4_6, R.drawable.ic_4_7, R.drawable.ic_4_8, R.drawable.ic_4_9},
            {R.drawable.ic_5_0, R.drawable.ic_5_1, R.drawable.ic_5_2, R.drawable.ic_5_3, R.drawable.ic_5_4, R.drawable.ic_5_5, R.drawable.ic_5_6, R.drawable.ic_5_7, R.drawable.ic_5_8, R.drawable.ic_5_9},
            {R.drawable.ic_6_0, R.drawable.ic_6_1, R.drawable.ic_6_2, R.drawable.ic_6_3, R.drawable.ic_6_4, R.drawable.ic_6_5, R.drawable.ic_6_6, R.drawable.ic_6_7, R.drawable.ic_6_8, R.drawable.ic_6_9},
            {R.drawable.ic_7_0, R.drawable.ic_7_1, R.drawable.ic_7_2, R.drawable.ic_7_3, R.drawable.ic_7_4, R.drawable.ic_7_5, R.drawable.ic_7_6, R.drawable.ic_7_7, R.drawable.ic_7_8, R.drawable.ic_7_9},
            {R.drawable.ic_8_0, R.drawable.ic_8_1, R.drawable.ic_8_2, R.drawable.ic_8_3, R.drawable.ic_8_4, R.drawable.ic_8_5, R.drawable.ic_8_6, R.drawable.ic_8_7, R.drawable.ic_8_8, R.drawable.ic_8_9},
            {R.drawable.ic_9_0, R.drawable.ic_9_1, R.drawable.ic_9_2, R.drawable.ic_9_3, R.drawable.ic_9_4, R.drawable.ic_9_5, R.drawable.ic_9_6, R.drawable.ic_9_7, R.drawable.ic_9_8, R.drawable.ic_9_9}};




    Runnable run = new Runnable() {
        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {
            c = Calendar.getInstance();
            currentdate = df.format(c.getTime());
            if(!Objects.equals(initialDate,currentdate)){
                reset();
                data = getDataHolder();
            }
           // if(refreshed)
           //     updatewpData();
            //wnData = (TrafficStats.getTotalRxBytes()-TrafficStats.getMobileRxBytes())-wpData;
            //wnUploaded =(TrafficStats.getTotalTxBytes()-TrafficStats.getMobileTxBytes())-wpUploaded;
            updatewnData();
            wSpeed = wNow - wPrevious;
            wPrevious = wNow;
            wNow = TrafficStats.getTotalRxBytes()-TrafficStats.getMobileRxBytes();
            data.wDownloaded += wSpeed;
            wnData = data.wDownloaded;
            updateWifiNotification();
            handler.postDelayed(run,1000);

        }
    };
    public void updatewpData(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        if(refreshed){
            wpData = TrafficStats.getTotalRxBytes()-TrafficStats.getMobileRxBytes();
            wpUploaded = TrafficStats.getTotalTxBytes()-TrafficStats.getMobileTxBytes();
            editor.putLong("wpUploaded",wpUploaded);
            editor.putLong("wpData",wpData);
            editor.apply();
            Intent intent = new Intent(this,wdRefresh.class);
            stopService(intent);
            refreshed = false;
        }else{

            wpData = preferences.getLong("wpData",0);
        }
    }
    public void updatewnData(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("wnData",wnData);

        editor.apply();

    }

    void stopRun(){
        handler.removeCallbacks(run);
    }

    void startRun(){
        run.run();
    }


    void updateWifiNotification(){
        int k=1,b=0,k1=1,b1=0;

        String [] unitArray = new String[]{
                "B", "KB", "MB", "GB"
        };

        if(wSpeed < 1024){
            k = 1;
            b=0;
        } else if (wSpeed > 1073741824){
            k = 1073741824;
            b=3;
        } else if (wSpeed > (1048576)) {
            k =  1048576;
            b=2;
        } else if (wSpeed > 1024) {
            k = 1024;
            b=1;
        }
        int temp1=0,temp2;

        if(wnData < 1024){
            temp1 = (int)(wnData);
            temp2 = (int)(wnData/0.01);
            temp1 = temp2 - (temp1*100);
            k1 = 1;
            b1=0;
        } else if (wnData > 1073741824){
            temp1 = (int)(wnData/1073741824);
            temp2 = (int)(wnData/10737418.24);
            temp1 = temp2 - (temp1*100);
            k1 = 1073741824;
            b1=3;
        } else if (wnData > (1048576)) {
            temp1 = (int)(wnData/1048576);
            temp2 = (int)(wnData/10485.76);
            temp1 = temp2 - (temp1*100);
            k1 =  1048576;
            b1=2;
        } else if (wnData > 1024) {
            temp1 = (int)(wnData/1024);
            temp2 = (int)(wnData/10.24);
            temp1 = temp2 - (temp1*100);
            k1 = 1024;
            b1=1;
        }

        if (wSpeed < 0){
            Intent intent = new Intent(this,wifiService.class);
            stopService(intent);
            ConnectivityManager manager = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if((info!=null)&&(info.isConnected())){
                Intent intent1 = new Intent(this,mobileDataService.class);
                startService(intent1);
            }
        }
        int kbps=0,mbps=0,flag = 0;
        if (b == 0) {
            kbps = 0;
        }else if(b == 1){
            kbps = (int) wSpeed / k;
            if(kbps > 853){

                mbps = 0;
                kbps = 9;
                flag = 1;
            }
        }else if(b == 2) {
            float temp = (int) wSpeed/1048576;
            mbps = (int)temp;
            if(mbps > 9)
                mbps = 9;
            kbps = (int)(wSpeed/104857.6);
            kbps = kbps - (mbps*10);
            flag = 1;
            if(kbps > 9)
                kbps = 9;

        }else{
            kbps = 9;
            mbps = 9;
        }

        Intent intentp = new Intent(this,wdRefresh.class);
        PendingIntent pintent = PendingIntent.getService(this,0,intentp,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle("Today's Wifi Usage")
                .setOngoing(true)
                .setPriority(5)
                .setContentText("Downloaded: " +wnData/k1 +"."+temp1+" "+unitArray[b1])
                .addAction(R.drawable.ic_replay_white_24dp,"REFRESH",pintent);
        if(flag == 0)
            builder.setSmallIcon(kbps_array[kbps]);
        else if(flag == 1)
            builder.setSmallIcon(mbps_array[mbps][kbps]);

        Intent intent = new Intent(getApplicationContext(),MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(intent);
        stackBuilder.addParentStack(MainActivity.class);


        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);


        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1,builder.build());
    }

    public long getWifiDataDownloaded(){
        return wnData;
    }
    public long getWifiDataUploaded(){
        return wnUploaded;
    }
    public int getflag() { return flag; }
    public int getMbps() {return mbps; }
    public int getKbps() {return kbps; }

    public void onDestroy(){
        Intent intent = new Intent(this,addingService.class);
        startService(intent);
        stopRun();
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(1);

    }


    public static void refresh(){

        refreshed = true;


    }

    void reset(){
        Intent intent = new Intent(this,addingService.class);
        startService(intent);
        Intent intent1 = new Intent(this,initialisationService.class);
        startService(intent1);
    }

    public dataHolder getDataHolder(){
        ArrayList<dataHolder> data = new ArrayList<>();
        try {
            FileInputStream in = openFileInput("data");
            ObjectInputStream ois = new ObjectInputStream(in);

            data = (ArrayList<dataHolder>) ois.readObject();
            ois.close();

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return data.get(data.size()-1);

    }




}
